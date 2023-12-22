package core.transaction;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PaymentDetails {
    private final Long paymentId;

    private final Long sellerId;

    private final Long customerID;

    private final Double amount;

    private String hash;

    public PaymentDetails(Long paymentId, Long sellerId, Long customerID, Double amount) {
        this.paymentId = paymentId;
        this.sellerId = sellerId;
        this.customerID = customerID;
        this.amount = amount;
    }

    private void generateHash(){
        String dataToHash =
                Long.toString(paymentId)
                + Long.toString(sellerId)
                + Long.toString(customerID)
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
