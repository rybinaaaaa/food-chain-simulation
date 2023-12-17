package core.transaction;

import core.channel.Channel;
import core.model.party.Party;
import core.operation.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Transaction {
    private Long id;

    private Party party;

    private Operation operation;

    private String hash;

    private Transaction previousTransaction;

    private static final Logger logger = LogManager.getLogger(Channel.class);


    public Transaction(Long id, Party party, Operation operation) {
        this.id = id;
        this.party = party;
        this.operation = operation;
    }

    public String createHash(){
        String dataToHash = previousTransaction.getHash()
                + Long.toString(id)
                + party.getFirstName() + party.getLastName()
                + operation.getName();
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            //log the error
        }
        StringBuilder buffer = new StringBuilder();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        this.hash = buffer.toString();
        return hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Transaction getPreviousTransaction() {
        return previousTransaction;
    }

    public void setPreviousTransaction(Transaction previousTransaction) {
        this.previousTransaction = previousTransaction;
    }

    public Long getId() {
        return id;
    }
}
