package com.bendarsianass.shops.exception;

import java.util.Map;

public class AccountServiceException extends RuntimeException {

    private Map<String, String> errorMap;
    public AccountServiceException(Map<String, String> errorMap) {
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }

    public void setErrorMap(Map<String, String> errorMap) {
        this.errorMap = errorMap;
    }
}
