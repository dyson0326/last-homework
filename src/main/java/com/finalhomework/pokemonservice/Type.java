package com.finalhomework.pokemonservice;

public class Type {

    private Integer id;
    private String type;

    public Type(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
