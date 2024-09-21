package com.finalhomework.pokemonservice;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PokemonMapper {

    @Select("SELECT * FROM pokemon")
    List<Name> findAll();

    @Select("SELECT * FROM pokemon WHERE name LIKE CONCAT(#{startsWith}, '%')")
    List<Name> findByNameStartingWith(String startsWith);

    @Select("SELECT * FROM pokemon WHERE id = #{id}")
    Optional<Name> findById(int id);
    
}
