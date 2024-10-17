package com.finalhomework.pokemonservice.Excption;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(String message) {
        super(message);
    }

}
