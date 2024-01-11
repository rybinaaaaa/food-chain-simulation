package Core.Product;

import Patterns.Prototype.Prototype;

public class Carrot extends Product {

    public  Carrot (Long id, Double weight, String name){
        super(id, weight, name);
    }
    public Carrot(Long id, Double weight, String name, Double price, int amount, AmountUnit unit) {
        super(id, weight, name, price, amount, unit);
    }

    @Override
    public Prototype<Product> clone() {
       return new Carrot(id, weight, name, price, amount, unit);
    }
}
