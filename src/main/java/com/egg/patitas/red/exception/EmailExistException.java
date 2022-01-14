package com.egg.patitas.red.exception;

public class EmailExistException extends Exception{
    private final static String EMAIL_EXIST_MESSAGE = "Existe una cuenta con este correo electrónico: ";

    private static final long serialVersionUID = 1L;

    public EmailExistException(String email) {
        super(EMAIL_EXIST_MESSAGE + email);
    }
}
