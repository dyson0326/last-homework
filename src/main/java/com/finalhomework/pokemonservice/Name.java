package com.finalhomework.pokemonservice;

import java.util.Objects;

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

    public Name(String name, String type1, String type2) {
        this.id = null;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(id, name1.id) && Objects.equals(name, name1.name) && Objects.equals(type1, name1.type1) && Objects.equals(type2, name1.type2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type1, type2);
    }

    @Override
    public String toString() {
        return "Name{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                '}';
    }
}
