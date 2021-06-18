package pt.deliveries.deliveries_engine.Pojo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


public class CreateDeliveryPojo implements Serializable {

    @NotBlank
    private Long order_id;

    @NotBlank
    private Long courier_id;

    private Long vehicle_id;


    public CreateDeliveryPojo(Long courier_id, Long order_id){
        this.order_id = order_id;
        this.courier_id = courier_id;
        this.vehicle_id = null;
    }

    public CreateDeliveryPojo(){}

    public CreateDeliveryPojo(Long courier_id, Long order_id, Long vehicle_id){
        this.order_id = order_id;
        this.courier_id = courier_id;
        this.vehicle_id = vehicle_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public Long getCourier_id() {
        return courier_id;
    }

    public Long getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(Long vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
}
