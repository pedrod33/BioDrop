package pt.deliveries.deliveries_engine.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRatingScoreException extends RuntimeException {
    public InvalidRatingScoreException(String message) {
        super(message);
    }
}
