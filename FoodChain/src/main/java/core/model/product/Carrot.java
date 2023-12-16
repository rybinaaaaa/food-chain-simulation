package core.model.product;

public class Carrot extends ProductPrototype{

    public Carrot(ProductPrototype prototype) {
        super(prototype);
    }

    public  Carrot (Long id, Double weight, String name){
        super(id, weight, name);
    }

    @Override
    public ProductPrototype clone() {
        return new Carrot(this);
    }


}
