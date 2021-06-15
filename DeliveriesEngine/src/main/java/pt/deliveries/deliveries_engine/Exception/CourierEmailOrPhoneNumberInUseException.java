package pt.deliveries.deliveries_engine.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.IM_USED)
public class CourierEmailOrPhoneNumberInUseException extends RuntimeException {
    public CourierEmailOrPhoneNumberInUseException(String message)
    {
        super(message);
    }
}
