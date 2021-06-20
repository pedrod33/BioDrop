package pt.deliveries.deliveries_engine.Pojo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


public class CreateDeliveryPojo implements Serializable {

    @NotBlank
    private long order_id;

    private long vehicle_id;


    public CreateDeliveryPojo(Long order_id){
        this.order_id = order_id;
        this.vehicle_id = -1;
    }

    public CreateDeliveryPojo(){}

    public CreateDeliveryPojo(long order_id, long vehicle_id){
        this.order_id = order_id;
        this.vehicle_id = vehicle_id;
    }

    public Long getOrder_id() {
        return order_id;
    }


    public long getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(Long vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
}
