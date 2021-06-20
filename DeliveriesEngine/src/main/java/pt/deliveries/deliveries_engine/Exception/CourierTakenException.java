package pt.deliveries.deliveries_engine.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.IM_USED)
public class CourierTakenException extends RuntimeException{
    public CourierTakenException(String message){
        super(message);
    }
}
