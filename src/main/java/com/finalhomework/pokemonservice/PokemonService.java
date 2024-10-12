package com.finalhomework.pokemonservice.service;

import com.finalhomework.pokemonservice.Name;
import com.finalhomework.pokemonservice.PokemonMapper;
import com.finalhomework.pokemonservice.PokemonNotFoundException;
import com.finalhomework.pokemonservice.Trainer;
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

    public List<Name> getName(String startsWith) {
        List<Name> names;
        if (Objects.nonNull(startsWith)) {
            names = pokemonMapper.findByNameStartingWith(startsWith);
        } else {
            names = pokemonMapper.findAll();
        }
        return names;
    }

    public Name findNameById(int id) {
        Optional<Name> name = pokemonMapper.findById(id);
        return name.orElseThrow(() -> new PokemonNotFoundException("pokemon not found"));
    }

    public Trainer insert(String name, String type1, String type2) {
//        String sql = "SELECT * FROM pokemon WHERE name";
//        List<String> nameList = sql.getString("name");
        Trainer trainer = new Trainer(name, type1, type2);
        trainerMapper.insert(trainer);
        return trainer;
    }

}
