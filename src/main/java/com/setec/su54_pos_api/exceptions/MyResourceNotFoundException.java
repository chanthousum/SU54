package com.setec.spring_demo_api.exceptions;

public class MyResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MyResourceNotFoundException(String msg) {
        super(msg);
    }
}