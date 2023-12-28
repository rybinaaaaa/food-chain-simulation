package core.product;

import patterns.prototype.Prototype;

public class Carrot extends Product {

    public  Carrot (Long id, Double weight, String name){
        super(id, weight, name);
    }
    public Carrot(Double weight, String name, Double price, int amount, AmountUnit unit) {
        super(weight, name, price, amount, unit);
    }

    @Override
    public Prototype<Product> clone() {
       return new Carrot(weight, name, price, amount, unit);
    }
}
