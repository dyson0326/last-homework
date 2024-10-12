package com.finalhomework.pokemonservice.controller;

import com.finalhomework.pokemonservice.Name;
import com.finalhomework.pokemonservice.Trainer;
import com.finalhomework.pokemonservice.TrainerRequest;
import com.finalhomework.pokemonservice.TrainerResponse;
import com.finalhomework.pokemonservice.service.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

    @PostMapping("/trainers")
    public ResponseEntity<TrainerResponse> insert(@RequestBody TrainerRequest trainerRequest, UriComponentsBuilder uriBuilder) {
        Trainer trainer = trainerService.insert(trainerRequest.getName(), trainerRequest.getType1(), trainerRequest.getType2());
        URI location = uriBuilder.path("/trainers/{id}").buildAndExpand(trainer.getId()).toUri();
        TrainerResponse body = new TrainerResponse("trainers created");
        return ResponseEntity.created(location).body(body);
    }

}
