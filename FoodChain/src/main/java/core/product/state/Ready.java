package core.product.state;

import core.product.Product;

public class Ready extends State{
    public Ready(Product product) {
        super(product);
        process();
    }

    @Override
    public void process(){
        logger.info("The product is ready for transition to the next party");
    }

}
