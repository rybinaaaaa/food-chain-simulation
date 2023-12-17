package core.model.product;

import core.model.product.state.ProductState;
import core.model.product.state.Ready;
import core.model.product.state.Received;

import java.util.List;

public abstract class ProductPrototype {
    private Long id;

    private Double weight;

    private String name;

    private Double price;

    private int amount;

    private List<Certificate> certificates;

    private AmountUnit unit;

    private ProductState state;

    public ProductPrototype(){}

    public ProductPrototype(ProductPrototype prototype) {
        prototype.id = id;
        prototype.weight = weight;
        prototype.name = name;
        prototype.price = price;
        prototype.amount = amount;
        prototype.certificates = certificates;
        prototype.unit = unit;
        prototype.state = new Received(this);
    }

    public ProductPrototype(Long id, Double weight, String name){
        this.id = id;
        this.weight = weight;
        this.name = name;
    }

    public abstract ProductPrototype clone();

    public ProductState getState() {
        return state;
    }

    public void setState(ProductState state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }
}
