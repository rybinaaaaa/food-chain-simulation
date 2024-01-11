package Core.Party;

import Core.Certificate.Certificate;
import Core.Product.Product;
import Core.Product.State.Received;
import Core.Operation.Operation;
import Core.Transaction.Account;
import Core.Channel.Channel;
import Exception.CertificateNotFoundException;
import Exception.NoCustomerFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public abstract class Party {
    final private UserKey key;

    final private String firstName;

    final private String lastName;

    private Account account;

    final protected List<Channel> channels = new ArrayList<>();

    protected Product product;

    protected Operation operation;

    protected static final Logger logger = LogManager.getLogger(Channel.class);

    private final List<Certificate> certificates = new ArrayList<>();

    private Boolean isRetroactiveChange = false;


    public Party(UserKey key, String firstName, String lastName) {
        this.key = key;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Conduct an operation on a product by changing its states.
     * @param product Particular product
     */

    public void processProduct(Product product) {
        this.setProduct(product);
        product.setState(new Received(product));
        publishEvent();
    }

    /**
     * Notifies channels about the product.
     */

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

    /**
     * Receives a notification from a channel.
     * @param o Operation conducted on a product
     * @param p Particular product
     * @param c Channel which sent the notification
     * @return consent to the product purchase
     */

    public Boolean update(Operation o, Product p, Channel c) {
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

    public void setRetroactiveChange(Boolean retroactiveChange) {
        isRetroactiveChange = retroactiveChange;
    }

    public Boolean isRetroactiveChange() {
        return isRetroactiveChange;
    }

    /**
     * Finds a certificate of the party.
     * @param product Particular product
     * @param operation Operation which party performs on the product
     * @return Found certificate or null
     * @throws CertificateNotFoundException Exception if no certificate was found
     */

    public Certificate getCertificateByProductAndOperation(Product product, Operation operation) throws CertificateNotFoundException {
        if (Objects.isNull(certificates)) throw new CertificateNotFoundException();
        return this.certificates.stream().filter(c -> Objects.equals(c.getProductId(), product.getId()) && c.getOperationClass() == operation.getClass()).findAny().orElseThrow(CertificateNotFoundException::new);
    }


    public void addCertificates(Certificate ...certificates) {
        for (Certificate c: certificates) {
            Certificate existingCertificate = this.certificates.stream().filter(certificate -> Objects.equals(certificate.getProductId(), c.getProductId()) && certificate.getOperationClass() == c.getOperationClass()).findAny().orElseGet(() -> {
                this.certificates.add(c);
                return c;
            });
            if (existingCertificate != c) {
                logger.error("Attempting to add an existing certificate!");
            }
        }
    }
}
