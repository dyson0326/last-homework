package com.finalhomework.pokemonservice;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PokemonMapper {

    //全件取得
    @Select("SELECT * FROM pokemon")
    List<Name> findAll();

    //指定された最初の文字を検索
    @Select("SELECT * FROM pokemon WHERE name LIKE CONCAT(#{startsWith}, '%')")
    List<Name> findByNameStartingWith(String startsWith);

    //指定されたIDで検索
    @Select("SELECT * FROM pokemon WHERE id = #{id}")
    Optional<Name> findById(int id);

    //指定された内容を挿入
    @Insert("INSERT INTO pokemon (name, type1, type2) VALUES (#{name}, #{type1}, #{type2})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Name newName);

    //指定されたIDのデータを更新する
    @Update("UPDATE pokemon SET name = #{name}, type1 = #{type1}, type2 = #{type2} WHERE id = #{id}")
    void update(Name updateName);

    //指定されたIDのデータを削除する
    @Delete("DELETE FROM pokemon WHERE id = #{id}")
    int deleteId(int id);

    @Select("SELECT * FROM TypeList")
    List<Type> AllType();

}
