package pt.deliveries.deliveries_engine.Service;

import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Pojo.RegisterCourierPojo;


public interface CourierService {
    Courier save(RegisterCourierPojo courier);

    boolean exists(RegisterCourierPojo courier);

}
