package com.customersProducts.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SystemErrorCode {

    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "Customer not Found"),
    REQUEST_BODY_EMPTY(HttpStatus.BAD_REQUEST, "Request body is Empty"),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "Product not Found"),;

    private final HttpStatus status;
    private final String message;
}

