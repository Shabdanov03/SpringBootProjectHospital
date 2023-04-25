package peaksoft.enums.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Shabdanov Ilim
 **/
@ResponseStatus(HttpStatus.NOT_FOUND)

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
    super(message);
    }
}
