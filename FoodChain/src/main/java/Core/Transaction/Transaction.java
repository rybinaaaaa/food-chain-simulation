package Core.Transaction;

import Core.Party.Party;
import Core.Operation.Operation;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Transaction {
    private final Long id;

    private final Party party;

    private final Operation operation;

    private String hash;

    private Transaction previousTransaction;

    private final PaymentDetails paymentDetails;

    private TransactionResult transactionResult;


    public Transaction(Long id, Party party, Operation operation, PaymentDetails paymentDetails, Transaction previousTransaction) {
        this.id = id;
        this.party = party;
        this.operation = operation;
        this.paymentDetails = paymentDetails;
        this.previousTransaction = previousTransaction;
        this.hash = createHash();
    }

    /**
     * Checks if current transaction is valid by comparing hash.
     * @return true if valid, false if invalid
     */

    public Boolean isValid(){
        return hash.equals(createHash());
    }

    /**
     * Creates hash for current transaction.
     * @return Created hash
     */

    public String createHash(){
        String dataToHash;
        if(previousTransaction == null){
            dataToHash = "null"
                    + Long.toString(id)
                    + party.getFirstName() + party.getLastName()
                    + paymentDetails.getHash()
                    + operation.getName();
        } else {
            dataToHash = previousTransaction.getHash()
                    + Long.toString(id)
                    + party.getFirstName() + party.getLastName()
                    + paymentDetails.getHash()
                    + operation.getName();
        }
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
        return buffer.toString();
    }

    public String getHash() {
        return hash;
    }

    public void setPreviousTransaction(Transaction previousTransaction) {
        this.previousTransaction = previousTransaction;
    }

    public Long getId() {
        return id;
    }

    public Party getParty() {
        return party;
    }

    public Operation getOperation() {
        return operation;
    }

    public TransactionResult getTransactionResult() {
        return transactionResult;
    }

    public void setTransactionResult(TransactionResult transactionResult) {
        this.transactionResult = transactionResult;
    }
}
