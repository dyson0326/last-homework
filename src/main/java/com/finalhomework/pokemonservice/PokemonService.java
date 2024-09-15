package com.finalhomework.pokemonservice;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonService {

    private PokemonMapper pokemonMapper;

    public PokemonService(PokemonMapper pokemonMapper) {

        this.pokemonMapper = pokemonMapper;
    }

    public List<Name> getName(String startsWith) {

        return pokemonMapper.findByNameStartingWith(startsWith);
    }

    public List<Name> allName() {

        return pokemonMapper.findAll();
    }

    public Name findNameById(int id) {
        Optional<Name> name = pokemonMapper.findById(id);
        return name.orElseThrow(() -> new PokemonNotFoundException("pokemon not found"));
    }

}
