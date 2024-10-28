package com.eatwell.foodHQ.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FoodHqValidationException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    public FoodHqValidationException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
