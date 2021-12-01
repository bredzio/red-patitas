package com.egg.patitas.red.exception;

public class EmailExistException extends Exception{
    private final static String EMAIL_EXIST_MESSAGE = "There is an account with that email address: ";

    private static final long serialVersionUID = 1L;

    public EmailExistException(String email) {
        super(EMAIL_EXIST_MESSAGE + email);
    }
}
