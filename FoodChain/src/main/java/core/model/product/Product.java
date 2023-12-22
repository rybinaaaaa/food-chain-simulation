package core.model.product;

import core.model.product.state.State;
import patterns.prototype.Prototype;

import java.util.List;

public abstract class Product implements Prototype<Product> {
    private Long id;

    private Double weight;

    private String name;

    private Double price;

    private int amount;

    private List<Certificate> certificates;

    private AmountUnit unit;

    private State state;

    public Product(){}

    @Override
    public void clone(Product prototype) {
        prototype.id = id;
        prototype.weight = weight;
        prototype.name = name;
        prototype.price = price;
        prototype.amount = amount;
        prototype.certificates = certificates;
        prototype.unit = unit;
//        prototype.state = ProductState.RECEIVED;
    }

    public Product(Long id, Double weight, String name){
        this.id = id;
        this.weight = weight;
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }
}
