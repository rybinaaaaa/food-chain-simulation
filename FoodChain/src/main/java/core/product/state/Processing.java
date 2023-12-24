package core.product.state;

import core.product.Product;

public class Processing extends State {

    public Processing(Product product) {
        super(product);
        this.timeToProcess = 4000;
    }

    @Override
    public void setNextState(){
        context.setState(new Ready(context));
    }

    @Override
    public void process(){
        logger.info("The operation is in process");
        try {
            Thread.sleep(timeToProcess);
        } catch (InterruptedException e) {
            // Handle the exception if the sleep is interrupted
            e.printStackTrace();
        }
        setNextState();
    }
}
