package pt.deliveries.deliveries_engine.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CourierEmailAndPasswordDoNotMatchException extends RuntimeException {
    public CourierEmailAndPasswordDoNotMatchException(String message)
    {
        super(message);
    }
}
