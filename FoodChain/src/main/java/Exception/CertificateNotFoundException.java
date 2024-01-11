package Exception;

public class CertificateNotFoundException extends RuntimeException {
    public CertificateNotFoundException() {
        super("The certificate wasn't found");
    }
}
