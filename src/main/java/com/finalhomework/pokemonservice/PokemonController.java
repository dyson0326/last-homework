package com.finalhomework.pokemonservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PokemonController {

    private PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {

        this.pokemonService = pokemonService;
    }

    @GetMapping("/names")
    public List<Name> getName(@RequestParam(value = "startsWith", required = false) String startsWith) {
        List<Name> names = pokemonService.getName(startsWith);
        return names;
    }

    @GetMapping("/names/{id}")
    public Name getName(@PathVariable("id") Integer id) {
        Name name = pokemonService.findNameById(id);
        return name;
    }

}
