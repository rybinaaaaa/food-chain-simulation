package core.model.product.state;

import core.model.product.ProductPrototype;

//state transition inside one party

public abstract class ProductState {

    private final ProductPrototype product;

    public ProductState(ProductPrototype product) {
        this.product = product;
    }
}
