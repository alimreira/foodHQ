package com.eatwell.foodHQ.errorResponse;

import com.eatwell.foodHQ.exception.FoodHqValidationException;
import com.eatwell.foodHQ.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException (ResourceNotFoundException resourceNotFoundException,
                                                                         WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                resourceNotFoundException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FoodHqValidationException.class)
    public ResponseEntity<ErrorDetails> handleFoodHqValidationException (FoodHqValidationException foodHqValidationException,

                                                                         WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                foodHqValidationException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


}
