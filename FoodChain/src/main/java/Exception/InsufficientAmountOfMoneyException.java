package Exception;

public class InsufficientAmountOfMoneyException extends RuntimeException{
    public InsufficientAmountOfMoneyException(){
        super("Not enough money on the customer account!");
    }

}
