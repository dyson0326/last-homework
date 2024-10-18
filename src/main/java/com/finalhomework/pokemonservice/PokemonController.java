package com.finalhomework.pokemonservice;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    //全件検索及び部分検索
    @GetMapping("/names")
    public List<Name> getName(@RequestParam(value = "startsWith", required = false) String startsWith) {
        List<Name> names = pokemonService.getName(startsWith);
        return names;
    }

    //ID検索
    @GetMapping("/names/{id}")
    public Name getName(@PathVariable("id") Integer id) {
        Name name = pokemonService.findNameById(id);
        return name;
    }

    //入力されたデータの挿入
    @PostMapping("/names")
    public ResponseEntity<NameResponse> insert(@RequestBody @Validated NameRequest nameRequest, UriComponentsBuilder uriBuilder) {
        Name name = pokemonService.insert(nameRequest.getName(), nameRequest.getType1(), nameRequest.getType2());
        URI location = uriBuilder.path("/names/{id}").buildAndExpand(name.getId()).toUri();
        NameResponse body = new NameResponse("pokemon created");
        return ResponseEntity.created(location).body(body);
    }

    //更新処理
    @PatchMapping("/names/{id}")
    public ResponseEntity<NameResponse> update(@PathVariable("id") Integer id, @RequestBody @Validated NameRequest nameRequest, UriComponentsBuilder uriBuilder) {
        Name name = pokemonService.update(id, nameRequest.getName(), nameRequest.getType1(), nameRequest.getType2());
        NameResponse body = new NameResponse("pokemon update");
        return ResponseEntity.ok(body);
    }

    //削除処理
    @DeleteMapping("/names/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        pokemonService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
