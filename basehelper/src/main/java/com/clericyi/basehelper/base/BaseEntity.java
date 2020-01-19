package com.clericyi.basehelper.base;

/**
 * author: ClericYi
 * time: 2020-01-19
 */
public class BaseEntity {

    private int responseCode;
    private String message;

    public BaseEntity setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public BaseEntity setMessage(String message) {
        this.message = message;
        return this;
    }
}
