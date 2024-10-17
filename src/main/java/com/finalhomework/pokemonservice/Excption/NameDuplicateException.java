package com.finalhomework.pokemonservice.Excption;

public class NameDuplicateException extends RuntimeException {
    public NameDuplicateException(String message) {
        super(message);
    }
}
