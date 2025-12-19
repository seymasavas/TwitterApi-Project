package TwitterApi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(TwitterException twitterException){
        log.error("TwitterException occured: " + twitterException);
        return new ResponseEntity<>(new ExceptionResponse(twitterException.getMessage(),twitterException.getHttpStatus().value(), LocalDateTime.now()),twitterException.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception exception){
        log.error("Exception occured: " + exception);
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
