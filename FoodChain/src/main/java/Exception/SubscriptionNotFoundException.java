package Exception;

public class SubscriptionNotFoundException extends RuntimeException{
    public SubscriptionNotFoundException() {
         super("No such subscription found");
    }
}
