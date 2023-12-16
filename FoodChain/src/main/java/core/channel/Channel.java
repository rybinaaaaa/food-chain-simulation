package core.channel;

import core.model.party.Party;
import core.operation.Operation;
import core.transaction.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Channel {

    private Long id;

    private List<Transaction> transactions;

    private Party seller;

    private Party customer;

    private ChannelType type;

//    private Map<Operation, Party> subscribers = new HashMap<>();

    private List<Party> subscribers = new ArrayList<>();

    public void subscribe(Party party) {
        subscribers.add(party);
    }

    public void unsubscribe(Party party) {
        subscribers.remove(party);
    }

//    public void notifyParties(){
//        for (Party subscriber: subscribers) {
//            subscriber.update();
//        }
//    }


    public Channel(ChannelType type) {
        this.type = type;
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

    public Party getSeller() {
        return seller;
    }

    public void setSeller(Party seller) {
        this.seller = seller;
    }

    public Party getCustomer() {
        return customer;
    }

    public void setCustomer(Party customer) {
        this.customer = customer;
    }

    public List<Party> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Party> subscribers) {
        this.subscribers = subscribers;
    }
}
