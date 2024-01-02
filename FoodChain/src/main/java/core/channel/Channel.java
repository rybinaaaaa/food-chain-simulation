package core.channel;

import core.certificate.Certificate;
import core.operation.Operation;
import core.party.Party;
import core.product.Product;
import core.transaction.Account;
import core.transaction.PaymentDetails;
import core.transaction.Transaction;
import core.transaction.TransactionResult;
import exception.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Channel {

    private Long id;

    private List<Transaction> transactions = new ArrayList<>();

    private ChannelType type;

    private final Map<Operation, List<Party>> subscribers = new HashMap<>();

    private final AtomicLong transactionIdCounter = new AtomicLong(System.currentTimeMillis());

    private static final Logger logger = LogManager.getLogger(Channel.class);


    public Channel(ChannelType type) {
        this.type = type;
    }


    /**
     * Adds a new subscriber to the subscribers list
     *
     * @param party     Party - subscriber
     * @param operation Operation to subscribe on
     */
    public void subscribe(Party party, Operation operation) {
        if (subscribers.containsKey(operation)) {
            List<Party> partyList = subscribers.get(operation);
            partyList.add(party);
        } else {
            List<Party> newPartyList = new ArrayList<>();
            newPartyList.add(party);
            subscribers.put(operation, newPartyList);
        }
        logger.info("Party " + party.getFullName() + " subscribed on " + operation.getName() + " operation.");
    }


    /**
     * Removes a subscriber from the subscribers list
     *
     * @param party     Party - subscriber
     * @param operation Operation to unsubscribe from
     */
    public void unsubscribe(Party party, Operation operation) throws SubscriptionNotFoundException {
        if (subscribers.containsKey(operation)) {
            List<Party> partyList = subscribers.get(operation);
            partyList.remove(party);

            if (partyList.isEmpty()) {
                subscribers.remove(operation);
            }
        } else {
            throw new SubscriptionNotFoundException();
        }
        logger.info("Party " + party.getFullName() + " unsubscribed from " + operation.getName() + " operation.");
    }

    /**
     * Notifies subscribers about the product and finds a customer for this product
     *
     * @param operation Operation conducted on a product
     * @param product   Particular product
     * @return Party-customer for the product
     */

    private Optional<Party> findCustomer(Operation operation, Product product) {
        return Optional.ofNullable(subscribers.entrySet().stream()
                .filter(entry -> entry.getKey().equals(operation))
                .flatMap(entry -> entry.getValue().stream())
                .filter(p -> p.update(operation, product, this)) // Filter parties where update returns true
                .findFirst() // Find the first party
                .orElse(null));
    }

    /**
     * Performs transition of a product between two parties, creates payment and transaction, checks validity of a certificate,
     * passes processing of a product to another party
     *
     * @param operation Operation conducted on a product by seller
     * @param product   Particular product
     * @param seller    Party which sells a product
     * @throws NoCustomerFoundException Exception if no customer was found for a product
     */

    public void publishPartyEvent(Operation operation, Product product, Party seller) throws NoCustomerFoundException {
        logger.info("Channel of type " + getType() + " is notifying subscribers about the " + product.getName());
        //find customer for the product
        Optional<Party> customer = findCustomer(operation, product);
        if (customer.isPresent()) {
            Transaction transaction = null;
            try {
                logger.info("Party " + customer.get().getFullName() + " wants to purchase " + product.getName());
                //process payment
                PaymentDetails paymentDetails = processPayment(seller, customer.get());
                //create transaction
                transaction = createTransaction(seller, operation, paymentDetails);
                //check the certificate
                Certificate certificate = seller.getCertificateByProductAndOperation(product, operation);

                if (!certificate.isActive()) {
                    logger.error("Party- " + customer.get().getFullName() + " tried to use inactive certificate! ");
                    transaction.setTransactionResult(TransactionResult.INACTIVE_CERTIFICATE);
                    product.addToHistory(transaction);
                    return;
                }
                //write to the product history
                product.addToHistory(transaction);

                logger.info("Party " + customer.get().getFullName() + " owns the " + product.getName());
                logger.info("The operation with " + seller.getFullName() + " and product " + product.getName() + " has been completed successfully");

                //pass the product to the customer
                seller.setProduct(null);
                //active the certificate
                certificate.setActive(false);
                customer.get().processProduct(product);
            } catch (CertificateNotFoundException e) {
                //сообщение о невозможности отправки продукта в каналы выписывается после пересылки этого продукта в эти каналы и его продажи
                logger.warn("Party " + seller.getFullName() + " has no certificate to send " + product.getName() + " to the channels");
                transaction.setTransactionResult(TransactionResult.CERTIFICATE_NOT_EXIST);
                product.addToHistory(transaction);
            } catch (InsufficientAmountOfMoneyException e) {
                logger.warn("Party " + customer.get().getFullName() + " does not have enough money to purchase " + product.getName());
                transaction.setTransactionResult(TransactionResult.NOT_ENOUGH_MONEY);
            } catch (InvalidHashException e) {
                logger.warn("Retroactive change attempt detected! Hash is invalid! Caused by party " + seller.getFullName());
            }
        } else {
            throw new NoCustomerFoundException();
        }
    }

    /**
     * Creates transaction
     *
     * @param seller         Party which sells a product
     * @param operation      Operation conducted on a product
     * @param paymentDetails Details of the performed payment
     * @return Created transaction
     * @throws InvalidHashException Exception in case of transaction modification attempt(invalid hash)
     */

    private Transaction createTransaction(Party seller, Operation operation, PaymentDetails paymentDetails) throws InvalidHashException {
        //create a transaction
        Transaction transaction;
        if (transactions.isEmpty()) {
            transaction = new Transaction(generateTransactionId(), seller, operation, paymentDetails, null);
        } else {
            transaction = new Transaction(generateTransactionId(), seller, operation, paymentDetails, transactions.get(transactions.size() - 1));
            if (seller.isRetroactiveChange()) {
                transaction.setPreviousTransaction(null);
            }
            logger.info("Transaction " + transaction.getId() + " has been created");
            //check the validity of the transaction
            if (!transaction.isValid()) {
                throw new InvalidHashException();
            }
        }
        //add the transaction to the transactions list
        transactions.add(transaction);
        logger.info("Transaction " + transaction.getId() + " has been created");
        transaction.setTransactionResult(TransactionResult.SUCCESS);
        return transaction;
    }

    /**
     * Processes payment and creates PaymentDetails
     *
     * @param seller   Party which sells a product
     * @param customer Party which buys a product
     * @return Created PaymentDetails
     * @throws InsufficientAmountOfMoneyException Exception if customer does not have enough money for the purchase
     */

    private PaymentDetails processPayment(Party seller, Party customer) throws InsufficientAmountOfMoneyException {
        Double price = seller.getOperation().getPrice();
        Account sellerAccount = seller.getAccount();
        Account customerAccount = customer.getAccount();

        //check if customer has enough money
        if (customerAccount.getTotalAmount() < price) {
            throw new InsufficientAmountOfMoneyException();
        }
        //transfer money
        sellerAccount.setTotalAmount(sellerAccount.getTotalAmount() + price);

        customerAccount.setTotalAmount(customerAccount.getTotalAmount() - price);
        logger.info("Party " + customer.getFullName() + " has paid " + price + " to " + seller.getFullName());

        //create PaymentDetails
        return new PaymentDetails(generateTransactionId(), seller.getKey(), customer.getKey(), price);
    }

    private Long generateTransactionId() {
        return transactionIdCounter.getAndIncrement();
    }

    public ChannelType getType() {
        return type;
    }
}
