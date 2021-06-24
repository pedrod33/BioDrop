package pt.deliveries.deliveries_engine.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "delivery")
public class Delivery {


    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private int status;

    @Column(name="latStore")
    private double latStore;

    @Column(name="longStore")
    private double longStore;

    @Column(name="latClient")
    private double latClient;

    @Column(name="longClient")
    private double longClient;

    @Column(name = "orderId", nullable = false, unique = true)
    private Long orderId;

    @Column(name = "clientId", nullable = false)
    private Long clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courierId", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Courier courier;

    public Delivery(Courier courier, Long order_id, double latStore, double longStore, double latClient, double longClient, Long clientId) {
        this.status = 4;
        this.courier = courier;
        this.orderId = order_id;
        this.latStore = latStore;
        this.longStore = longStore;
        this.latClient = latClient;
        this.longClient = longClient;
        this.clientId = clientId;
    }

    public Delivery() {this.status = 4;}

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public Long getOrder_id() {
        return orderId;
    }

    public void setOrder_id(Long order_id) {
        this.orderId = order_id;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public double getLatStore() {
        return latStore;
    }

    public void setLatStore(double latStore) {
        this.latStore = latStore;
    }

    public double getLongStore() {
        return longStore;
    }

    public void setLongStore(double longStore) {
        this.longStore = longStore;
    }

    public double getLatClient() {
        return latClient;
    }

    public void setLatClient(double latClient) {
        this.latClient = latClient;
    }

    public double getLongClient() {
        return longClient;
    }

    public void setLongClient(double longClient) {
        this.longClient = longClient;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
