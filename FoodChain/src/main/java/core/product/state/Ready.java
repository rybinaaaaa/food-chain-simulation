package core.product.state;

import core.product.Product;

public class Ready extends State{
    public Ready(Product product) {
        super(product);
        this.timeToProcess = 2000;
    }

    @Override
    public void process(){
        logger.info("The product is ready for transition to the next party");
        try {
            Thread.sleep(timeToProcess);
        } catch (InterruptedException e) {
            // Handle the exception if the sleep is interrupted
            e.printStackTrace();
        }
    }

}
