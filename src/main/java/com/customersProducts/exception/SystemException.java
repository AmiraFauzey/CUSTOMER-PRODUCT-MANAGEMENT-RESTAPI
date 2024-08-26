package com.customersProducts.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SystemException extends RuntimeException{

    private final SystemErrorCode customerErrorCode;

}
