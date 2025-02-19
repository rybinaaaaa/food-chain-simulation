package Core.Party;

import Core.Product.Product;

public class Customer extends Party{

    public Customer(UserKey key, String firstName, String lastName) {
        super(key, firstName, lastName);
    }

    @Override
    public void processProduct(Product product){
        this.setProduct(product);
        logger.info("The product has achieved the final destination!" );

    }
}
