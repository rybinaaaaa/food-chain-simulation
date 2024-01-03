package core.product;

import core.product.state.State;
import core.transaction.Transaction;
import patterns.prototype.Prototype;

import java.util.ArrayList;
import java.util.List;

public class Product implements Prototype<Product> {
    protected Long id;

    protected Double weight;

    protected String name;

    protected Double price;

    protected int amount;

    protected AmountUnit unit;

    private State state;

    private List<Transaction> history = new ArrayList<>();

    public Product(){}

    @Override
    public Prototype<Product> clone() {
        return new Product(id, weight, name, price, amount, unit);
    }

    public Product(Long id, Double weight, String name, Double price, int amount, AmountUnit unit) {
        this.id = id;
        this.weight = weight;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.unit = unit;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void addToHistory(Transaction transaction) {
        this.history.add(transaction);
    }

    public List<Transaction> getHistory() {
        return history;
    }

    public Product(Long id, Double weight, String name){
        this.id = id;
        this.weight = weight;
        this.name = name;
    }


}
