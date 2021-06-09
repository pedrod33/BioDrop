package pt.deliveries.business_initiative.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Order_Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade=CascadeType.ALL)
    @JsonIgnore
    private Order order;

    @OneToOne
    Product product;

    @Column(nullable = false)
    private Integer amount;


    public Order_Product() { }

    public Order_Product(Order order, Product product, int amount) {
        this.order = order;
        this.product = product;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Order_Product{" +
                "id=" + id +
                ", product=" + product +
                ", amount=" + amount +
                '}';
    }
}
