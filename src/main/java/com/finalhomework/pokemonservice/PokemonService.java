package com.finalhomework.pokemonservice;

import com.finalhomework.pokemonservice.Excption.NameDuplicateException;
import com.finalhomework.pokemonservice.Excption.PokemonNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PokemonService {

    private PokemonMapper pokemonMapper;

    public PokemonService(PokemonMapper pokemonMapper) {

        this.pokemonMapper = pokemonMapper;
    }

    //startsWithに値があれば部分検索　なければ全件検索
    public List<Name> getName(String startsWith) {
        List<Name> names;
        if (Objects.nonNull(startsWith)) {
            names = pokemonMapper.findByNameStartingWith(startsWith);
        } else {
            names = pokemonMapper.findAll();
        }
        return names;
    }

    //IDで検索
    public Name findNameById(int id) {
        Optional<Name> name = pokemonMapper.findById(id);
        return name.orElseThrow(() -> new PokemonNotFoundException("pokemon not found"));
    }

    //指定された値を挿入　
    public Name insert(String name, String type1, String type2) {
        List<Name> nameList = pokemonMapper.findAll();
        //挿入時の重複チェック
        for (Name allname : nameList) {
            if (allname.getName().contains(name)) {
                throw new NameDuplicateException(name + "は既に存在しています");
            }
        }
        Name newName = new Name(name, type1, type2);
        pokemonMapper.insert(newName);
        return newName;
    }

    //指定されたIDのデータを更新する
    public Name update(int id, String name, String type1, String type2) {
        //存在しないIDの場合の処理
        Name idJudge = pokemonMapper.findById(id).orElseThrow(() -> new PokemonNotFoundException("存在しないIDです"));
        //更新処理
        Name updateName = new Name(id, name, type1, type2);
        pokemonMapper.update(updateName);
        return updateName;
    }

    //指定されたIDのデータを削除する
    public void delete(int id) {
        //存在しないIDの場合の処理
        Name idJudge = pokemonMapper.findById(id).orElseThrow(() -> new PokemonNotFoundException("存在しないIDです"));
        //削除処理
        pokemonMapper.deleteId(id);
    }

}
