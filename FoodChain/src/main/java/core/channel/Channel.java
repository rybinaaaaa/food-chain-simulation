package core.channel;

import core.certificate.Certificate;
import core.party.Party;
import core.product.Product;
import core.operation.Operation;
import core.transaction.Account;
import core.transaction.PaymentDetails;
import core.transaction.Transaction;
import exception.CertificateNotFoundException;
import exception.InsufficientAmountOfMoneyException;
import exception.NoCustomerFoundException;
import exception.SubscriptionNotFoundException;
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

    private Optional<Party> findCustomer(Operation operation, Product product){
        return Optional.ofNullable(subscribers.entrySet().stream()
                .filter(entry -> entry.getKey().equals(operation))
                .flatMap(entry -> entry.getValue().stream())
                .filter(p -> p.update(operation, product, this)) // Filter parties where update returns true
                .findFirst() // Find the first party
                .orElse(null));
    }

    public void publishPartyEvent(Operation operation, Product product, Party seller) throws NoCustomerFoundException {
        logger.info("Channel of type " + getType() + " is notifying subscribers about the " + product.getName());

        Optional<Party> customer = findCustomer(operation, product);
        if (customer.isPresent()) {
            try {
                Certificate certificate = seller.getCertificateByProductAndOperation(product, operation);
                logger.info("Party- " + customer.get().getFullName() + " accepts the " + product.getName());

                PaymentDetails paymentDetails = processPayment(seller, customer.get());
                Transaction transaction = createTransaction(seller, operation, paymentDetails);

                product.addToHistory(transaction);

                logger.info("Party " + customer.get().getFullName() + " owns the " + product.getName());
                logger.info("The operation with " + seller.getFullName() + " and product " + product.getName() + " has been completed successfully");

                seller.setProduct(null);
                certificate.setActive(false);
                customer.get().processProduct(product);

            } catch (CertificateNotFoundException e) {
                logger.warn("Party " + seller.getFullName() + " has no certificate to send " + product.getName() + " to the channels");
            } catch (InsufficientAmountOfMoneyException e) {
                logger.warn("Party " + customer.get().getFullName() + " does not have enough money to purchase " + product.getName());
            }
        } else {
            throw new NoCustomerFoundException();
        }
    }

    private Transaction createTransaction(Party seller, Operation operation, PaymentDetails paymentDetails) {
        Transaction transaction = new Transaction(generateTransactionId(), seller, operation, paymentDetails);
        try {
            transaction.setPreviousTransaction(transactions.get(transactions.size() - 1));
        } catch (IndexOutOfBoundsException e) {
            transaction.setPreviousTransaction(null);
        }
        transactions.add(transaction);
        logger.info("Transaction " + transaction.getId() + " has been created");
        return transaction;
    }

    private PaymentDetails processPayment(Party seller, Party customer) throws InsufficientAmountOfMoneyException {
        //transfer money
        Double price = seller.getOperation().getPrice();
        Account sellerAccount =  seller.getAccount();

        sellerAccount.setTotalAmount(sellerAccount.getTotalAmount() + price);

        Account customerAccount =  customer.getAccount();
        if(customerAccount.getTotalAmount() < price){
            throw new InsufficientAmountOfMoneyException();
        }
        customerAccount.setTotalAmount(customerAccount.getTotalAmount() - price);
        logger.info("Party " + customer.getFullName() + " has paid " + price + " to "  + seller.getFullName());


        //create PaymentDetails
        return new PaymentDetails(generateTransactionId(), seller.getKey(), customer.getKey(), price);
    }

    private Long generateTransactionId() {
        return transactionIdCounter.getAndIncrement();
    }

    public ChannelType getType() {
        return type;
    }

    public void setType(ChannelType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
