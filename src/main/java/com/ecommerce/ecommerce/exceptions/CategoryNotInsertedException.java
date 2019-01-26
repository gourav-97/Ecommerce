package com.ecommerce.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryNotInsertedException extends Exception {
    public CategoryNotInsertedException(String s) {
        super(s);
    }
}
