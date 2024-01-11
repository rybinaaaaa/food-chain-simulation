package Core.Party;


import java.security.*;
import java.util.Base64;

public class UserKey {
    private String privateKey;
    private String publicKey;

    public UserKey() {
        generateKey();
    }

    public void generateKey() {
        try {
            String algorithm = "RSA";
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

           this.publicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
           this.privateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }
}


