package core.entity.product;

import java.util.List;

public abstract class ProductPrototype {
    private Long id;

    private Double weight;

    private String name;

    private Double price;

    private int amount;

    private List<Certificate> certificates;

    private AmountUnit unit;

    public ProductPrototype(){}

    public ProductPrototype(ProductPrototype prototype) {
        prototype.id = id;
        prototype.weight = weight;
        prototype.name = name;
        prototype.price = price;
        prototype.amount = amount;
        prototype.certificates = certificates;
        prototype.unit = unit;
    }

    public abstract ProductPrototype clone();
}
