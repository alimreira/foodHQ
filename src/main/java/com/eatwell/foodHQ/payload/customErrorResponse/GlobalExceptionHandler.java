package com.eatwell.foodHQ.payload.customErrorResponse;

import com.eatwell.foodHQ.exception.FoodHqValidationException;
import com.eatwell.foodHQ.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest webRequest) {
        String resourceName = ex.getResourceName();
        String fieldName = ex.getFieldName();
        long fieldValue = ex.getFieldValue();
        String message = String.format("%s not found for %s with value %d", resourceName, fieldName, fieldValue);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FoodHqValidationException.class)
    public ResponseEntity<ErrorDetails> handleFoodHqValidationException(
            FoodHqValidationException ex, WebRequest webRequest) {
        // Extract the details from the exception
        HttpStatus httpStatus = ex.getHttpStatus();
        String message = ex.getMessage();
        // Create an ErrorDetails object with timestamp, message, and details
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, webRequest.getDescription(false));
        // Return the error details with the specified HTTP status
        return new ResponseEntity<>(errorDetails, httpStatus);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException accessDeniedException,
                                                                    WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),accessDeniedException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.UNAUTHORIZED);
    }

@ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(IllegalStateException illegalStateException,
                                                                    WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),illegalStateException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_ACCEPTABLE);
    }


}
