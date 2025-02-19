package Core.Product.State;

import Core.Product.Product;

public class Received extends State{
    public Received(Product product) {
        super(product);
        this.timeToProcess = 1000;
        process();
    }

    @Override
    public void setNextState(){
        context.setState(new Processing(context));
    }

    @Override
    public void process(){
        logger.info("The product has been received by the party");
        try {
            Thread.sleep(timeToProcess);
        } catch (InterruptedException e) {
            // Handle the exception if the sleep is interrupted
            e.printStackTrace();
        }
        setNextState();
    }

}
