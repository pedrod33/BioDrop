package pt.deliveries.business_initiative.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clientsOrder")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(cascade=CascadeType.ALL)
    private Set<Order_Product> orderProducts;

    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    @JsonIgnore
    private Client client;

    @Column(nullable = false)
    private String status;


    public Order() { }

    public Order(Address address, Set<Order_Product> orderProducts, Client client, String status) {
        this.address = address;
        this.orderProducts = orderProducts;
        this.client = client;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Order_Product> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<Order_Product> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", address=" + address +
                ", orderProducts=" + orderProducts +
                ", status='" + status + '\'' +
                '}';
    }
}
