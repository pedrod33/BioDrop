package pt.deliveries.deliveries_engine.Service;

import pt.deliveries.deliveries_engine.Model.Courier;

import java.util.Optional;

public interface CourierService {
    Courier save(Courier courier);

    boolean exists(Courier courier);

    Courier verifyLogin(Courier courier);

}
