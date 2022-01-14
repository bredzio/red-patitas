package com.egg.patitas.red.exception;

public class EmailNoExistException extends Exception{
    private final static String EMAIL_NO_EXIST_MESSAGE = "No existe una cuenta con este correo electrónico";

    private static final long serialVersionUID = 1L;

    public EmailNoExistException() {
        super(EMAIL_NO_EXIST_MESSAGE);
    }
}
