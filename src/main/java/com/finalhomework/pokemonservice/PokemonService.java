package com.finalhomework.pokemonservice;

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

}
