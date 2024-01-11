package Core.Transaction;

public class Account {
    private Long id;

    private Double totalAmount;

    public Account(Long id, Double totalAmount) {
        this.id = id;
        this.totalAmount = totalAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
