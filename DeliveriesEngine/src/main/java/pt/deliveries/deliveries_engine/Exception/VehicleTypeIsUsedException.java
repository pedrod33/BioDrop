package pt.deliveries.deliveries_engine.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.IM_USED)
public class VehicleTypeIsUsedException extends RuntimeException {
    public VehicleTypeIsUsedException(String message) {
        super(message);
    }
}
