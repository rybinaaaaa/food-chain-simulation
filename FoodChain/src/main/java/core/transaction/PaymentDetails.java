package core.transaction;

import core.party.UserKey;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PaymentDetails {
    private final Long paymentId;

    private final UserKey sellerId;

    private final UserKey customerID;

    private final Double amount;

    private String hash;

    public PaymentDetails(Long paymentId, UserKey sellerId, UserKey customerID, Double amount) {
        this.paymentId = paymentId;
        this.sellerId = sellerId;
        this.customerID = customerID;
        this.amount = amount;
    }

    private void generateHash(){
        String dataToHash =
                Long.toString(paymentId)
                + sellerId.getPublicKey()
                + customerID.getPublicKey()
                + Double.toString(amount);
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
    }

    public String getHash() {
        return hash;
    }
}
