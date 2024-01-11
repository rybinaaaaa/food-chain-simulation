package Core.Product.State;

import Core.Product.Product;

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
