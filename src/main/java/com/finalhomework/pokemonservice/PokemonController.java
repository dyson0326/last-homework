package com.finalhomework.pokemonservice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/pokemon")
public class PokemonController {

    private PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    //全件検索及び部分検索
    @GetMapping("/listnames")
    @ResponseBody
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
    @PostMapping("/newnames")
    public ResponseEntity<NameResponse> insert(@ModelAttribute @Validated NameRequest nameRequest, UriComponentsBuilder uriBuilder) {
        Name name = pokemonService.insert(nameRequest.getName(), nameRequest.getType1(), nameRequest.getType2());
        URI location = uriBuilder.path("/names/{id}").buildAndExpand(name.getId()).toUri();
        NameResponse body = new NameResponse("追加しました");
        return ResponseEntity.created(location).body(body);
    }

    //更新処理
    @PatchMapping("/updatenames/{id}")
    public ResponseEntity<NameResponse> update(@PathVariable("id") Integer id, @ModelAttribute @Validated NameRequest nameRequest, UriComponentsBuilder uriBuilder) {
        Name name = pokemonService.update(id, nameRequest.getName(), nameRequest.getType1(), nameRequest.getType2());
        NameResponse body = new NameResponse("更新しました");
        return ResponseEntity.ok(body);
    }

    //削除処理
    @DeleteMapping("/deletenames/{id}")
    public ResponseEntity<NameResponse> delete(@PathVariable("id") Integer id) {
        pokemonService.delete(id);
        NameResponse body = new NameResponse("削除しました");
        return ResponseEntity.ok(body);
    }

}
