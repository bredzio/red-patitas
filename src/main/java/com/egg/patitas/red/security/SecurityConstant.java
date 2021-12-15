package com.egg.patitas.red.security;

public interface SecurityConstant {
    public static final String ADMIN = "hasRole('ADMIN')";
    public static final String USER = "hasRole('USER'))";
    public static final String ADMIN_OR_USERAUTH = "hasRole('ADMIN') or #email == authentication.principal.username";
    public static final String ADMIN_AND_USER = "hasAnyRole('ADMIN','USER')";
}
