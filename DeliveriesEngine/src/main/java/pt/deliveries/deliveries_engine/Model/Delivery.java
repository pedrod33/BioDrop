package pt.deliveries.deliveries_engine.Model;

import javax.persistence.*;

@Entity
@Table(name = "delivery")
public class Delivery {

    private final int ACCEPTED = 0;
    private final int PICKED_UP = 1;
    private final int DELIVERED = 2;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private int status;

    @Column(name = "order_id", nullable = false, unique = true)
    private Long order_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id", referencedColumnName = "id", nullable = false)
    private Courier courier;

    public Delivery(Long id, String status, Courier courier, Vehicle vehicle, Long order_id) {
        this.status = this.ACCEPTED;
        this.courier = courier;
        this.vehicle = vehicle;
        this.order_id = order_id;
    }

    public Delivery() {this.status = this.ACCEPTED;}

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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }
}
