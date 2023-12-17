package core.model.product;

public class Carrot extends Product {

    public  Carrot (Long id, Double weight, String name){
        super(id, weight, name);
    }

    @Override
    public void clone(Product product) {
        super.clone(product);
    }
}
