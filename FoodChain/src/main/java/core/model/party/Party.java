package core.model.party;

import core.model.product.Product;
import core.model.product.state.Received;
import core.operation.Operation;
import core.transaction.Account;
import core.channel.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class Party {
    final private UserKey key;

    final private String firstName;

    final private String lastName;

    private Account account;

    final private List<Channel> channels = new ArrayList<>();

    private Product product;

    private Operation operation;

    protected static final Logger logger = LogManager.getLogger(Channel.class);


    public Party(UserKey key, String firstName, String lastName) {
        this.key = key;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void processProduct(Product product){
        this.setProduct(product);
        //states to do
        publishEvent();
    }

    public void publishEvent(){
        logger.info("Party " + getFullName() + " is sending " + product.getName() + " to the channels" );
        for(Channel channel: channels)
            channel.publishPartyEvent(operation, product, this);
    }

    public Boolean update(Operation o, Product p, Channel c){
        return true;
    }

    public  String getFullName(){
        return firstName + " " + lastName;
    }

    public UserKey getKey() {
        return key;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void addChannel(Channel channel) {
        this.channels.add(channel);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
