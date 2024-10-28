package org.leanpay.project.exception;

import lombok.extern.slf4j.Slf4j;
import org.leanpay.project.model.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import static java.util.Map.entry;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS = Map.ofEntries(
        entry(IllegalArgumentException.class, HttpStatus.BAD_REQUEST)
    );


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {

        var message = "Validation failed: " + ex.getMessage();

        return createErrorResponse(ex, message, EXCEPTION_STATUS.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<ErrorResponseDto> createErrorResponse(Exception ex,
                                                                 String message,
                                                                 HttpStatus status) {

        log.error("Exception occurred, captured in handler: {}", message, ex);
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setMessage(message);
        errorResponse.setStatusCode(status.value());
        errorResponse.setStatusCodeText(status.getReasonPhrase());

        return new ResponseEntity<>(errorResponse, status);
    }

}
