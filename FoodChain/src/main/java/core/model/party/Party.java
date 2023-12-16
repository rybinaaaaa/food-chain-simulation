package core.model.party;

import core.model.product.ProductPrototype;
import core.transaction.Account;
import core.channel.Channel;

import java.util.ArrayList;
import java.util.List;

public abstract class Party {
    private Long id;

    private String firstName;

    private String lastName;

    private Account account;

    private List<Channel> channels = new ArrayList<>();

    private ProductPrototype product;

    public Party(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void publishEvent(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public ProductPrototype getProduct() {
        return product;
    }

    public void setProduct(ProductPrototype product) {
        this.product = product;
    }
}
