package core.model.product;

public class Carrot extends ProductPrototype{

    public Carrot(ProductPrototype prototype) {
        super(prototype);
    }

    @Override
    public ProductPrototype clone() {
        return new Carrot(this);
    }
}
