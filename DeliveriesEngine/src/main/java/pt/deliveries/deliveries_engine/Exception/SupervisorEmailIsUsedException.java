package pt.deliveries.deliveries_engine.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.IM_USED)
public class SupervisorEmailIsUsedException extends RuntimeException{
    public SupervisorEmailIsUsedException(String message){
        super(message);
    }
}
