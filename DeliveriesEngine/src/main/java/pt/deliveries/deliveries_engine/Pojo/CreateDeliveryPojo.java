package pt.deliveries.deliveries_engine.Pojo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class CreateDeliveryPojo implements Serializable {

    @NotBlank
    private final Long order_id;

    @NotBlank
    private final Long courier_id;

    private Long vehicle_id;


    public CreateDeliveryPojo(Long courier_id, Long order_id){
        this.order_id = order_id;
        this.courier_id = courier_id;
    }

    public CreateDeliveryPojo(Long courier_id, Long order_id, Long vehicle_id){
        this.order_id = order_id;
        this.courier_id = courier_id;
        this.vehicle_id = vehicle_id;
    }
}
