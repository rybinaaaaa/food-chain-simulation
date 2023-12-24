package core.product;

import core.certificate.Certificate;
import core.product.state.State;
import patterns.prototype.Prototype;

import java.util.List;

public abstract class Product implements Prototype<Product> {
    private Long id;

    private Double weight;

    private String name;

    private Double price;

    private int amount;

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
//        prototype.certificates = certificates;
        prototype.unit = unit;
//        prototype.state = ProductState.RECEIVED;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public AmountUnit getUnit() {
        return unit;
    }

    public void setUnit(AmountUnit unit) {
        this.unit = unit;
    }

    public Product(Long id, Double weight, String name){
        this.id = id;
        this.weight = weight;
        this.name = name;
    }
}
