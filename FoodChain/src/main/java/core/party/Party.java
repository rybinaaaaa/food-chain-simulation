package core.party;

import core.certificate.Certificate;
import core.product.Product;
import core.operation.Operation;
import core.transaction.Account;
import core.channel.Channel;
import exception.CertificateNotFoundException;
import exception.NoCustomerFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public abstract class Party {
    final private UserKey key;

    final private String firstName;

    final private String lastName;

    private Account account;

    final private List<Channel> channels = new ArrayList<>();

    private Product product;

    private Operation operation;

    protected static final Logger logger = LogManager.getLogger(Channel.class);

    private List<Certificate> certificates = new ArrayList<>();


    public Party(UserKey key, String firstName, String lastName) {
        this.key = key;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void processProduct(Product product) {
        this.setProduct(product);
        //states to do
        publishEvent();
    }

    public void publishEvent() {
        logger.info("Party " + getFullName() + " is sending " + product.getName() + " to the channels");
       try {
           for (Channel channel : channels) {
               channel.publishPartyEvent(operation, product, this);
           }
       } catch(NoCustomerFoundException e){
           logger.info("No customer has been found for the "  + product.getName());
       }
    }

    public Boolean update(Operation o, Product p, Channel c) {
//        try {
//            Certificate certificate = this.getCertificateByProductAndOperation(p, o);
//            certificate.setActive(false);
//        } catch (CertificateNotFoundException e) {
//            logger.warn("Party " + getFullName() + " has no certificate to join channel with productId  " + p.getId() + " with operation " + o.getClass().getName());
//            return false;
//        }
        return true;
    }

    public String getFullName() {
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

    public Certificate getCertificateByProductAndOperation(Product product, Operation operation) throws CertificateNotFoundException {
        if (Objects.isNull(certificates)) throw new CertificateNotFoundException();
        return this.certificates.stream().filter(c -> Objects.equals(c.getProductId(), product.getId()) && c.getOperationClass() == operation.getClass()).findAny().orElseThrow(CertificateNotFoundException::new);
    }

    public void addCertificates(Certificate ...certificates) {
        for (Certificate c: certificates) {
            this.certificates.stream().filter(certificate -> Objects.equals(certificate.getProductId(), c.getProductId()) && certificate.getOperationClass() == c.getOperationClass()).findAny().orElseGet(() -> {
                this.certificates.add(c);
                return c;
            });
        }
    }
}
