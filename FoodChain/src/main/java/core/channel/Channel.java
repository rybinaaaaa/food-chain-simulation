package core.channel;

import core.model.party.Party;
import core.model.product.Product;
import core.operation.Operation;
import core.transaction.Account;
import core.transaction.PaymentDetails;
import core.transaction.Transaction;
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

    public void unsubscribe(Party party, Operation operation) {
        if (subscribers.containsKey(operation)) {
            List<Party> partyList = subscribers.get(operation);
            partyList.remove(party);

            if (partyList.isEmpty()) {
                subscribers.remove(operation);
            }
        } else {
            // log - no such subscription
        }
        logger.info("Party " + party.getFullName() + " unsubscribed from " + operation.getName() + " operation.");
    }

    public void publishPartyEvent(Operation operation, Product product, Party seller) {
        logger.info("Channel of type " + getType() + " is notifying subscribers about the " + product.getName());

        Optional<Party> customer = Optional.ofNullable(subscribers.entrySet().stream()
                .filter(entry -> entry.getKey().equals(operation))
                .flatMap(entry -> entry.getValue().stream())
                .filter(p -> p.update(operation, product, this)) // Filter parties where update returns true
                .findFirst() // Find the first party
                .orElse(null));
        if (customer.isPresent()) {
            logger.info("Party- " + customer.get().getFullName() + " accepts the " + product.getName());

            PaymentDetails paymentDetails = processPayment(seller, customer.get());
            createTransaction(seller, operation, paymentDetails);
            logger.info("Party " + customer.get().getFullName() + " owns the " + product.getName());

            customer.get().processProduct(product);
            seller.setProduct(null);
        }
    }

    private void createTransaction(Party seller, Operation operation, PaymentDetails paymentDetails) {
        Transaction transaction = new Transaction(generateTransactionId(), seller, operation, paymentDetails);
        try {
            transaction.setPreviousTransaction(transactions.get(transactions.size() - 1));
        } catch (IndexOutOfBoundsException e) {
            transaction.setPreviousTransaction(null);
        }
        transactions.add(transaction);
        logger.info("Transaction " + transaction.getId() + " has been created");
    }

    private PaymentDetails processPayment(Party seller, Party customer){
        //transfer money
        Double price = seller.getOperation().getPrice();
        Account sellerAccount =  seller.getAccount();
        sellerAccount.setTotalAmount(sellerAccount.getTotalAmount() + price);

        Account customerAccount =  customer.getAccount();
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
