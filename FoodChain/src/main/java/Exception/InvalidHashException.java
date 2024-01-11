package Exception;

public class InvalidHashException extends RuntimeException{
    public InvalidHashException(){
        super("Hash is invalid!");
    }
}
