package core.transaction;

import core.party.UserKey;
import core.report.Report;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PaymentDetails {
    private final Long paymentId;

    private final UserKey sellerId;

    private final UserKey customerID;

    private final Double amount;

    private final String hash;

    private static final Logger logger = LogManager.getLogger(Report.class);


    public PaymentDetails(Long paymentId, UserKey sellerId, UserKey customerID, Double amount) {
        this.paymentId = paymentId;
        this.sellerId = sellerId;
        this.customerID = customerID;
        this.amount = amount;
        this.hash = generateHash();
    }

    /**
     * Creates hash for current payment.
     * @return created hash
     */

    private String generateHash(){
        String dataToHash =
                Long.toString(paymentId)
                + sellerId.getPublicKey()
                + customerID.getPublicKey()
                + Double.toString(amount);
        MessageDigest digest;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            logger.error("No algorithm for hashing!");
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
}
