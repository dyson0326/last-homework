package com.finalhomework.pokemonservice;

public class Name {
    private Integer id;
    private String name;
    private String type1;
    private String type2;

    public Name(Integer id, String name, String type1, String type2) {
        this.id = id;
        this.name = name;
        this.type1 = type1;
        this.type2 = type2;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

}