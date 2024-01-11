package Exception;

public class NoCustomerFoundException extends RuntimeException{
    public NoCustomerFoundException(){
        super("No customer has been found for this product");
    }
}
