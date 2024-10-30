package com.finalhomework.pokemonservice;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PokemonMapperTest {

    @Autowired
    PokemonMapper pokemonMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @Transactional
    void すべてのポケモンを取得() {
        List<Name> pokemon = pokemonMapper.findAll();
        assertThat(pokemon)
                .hasSize(9)
                .contains(
                        new Name(1, "キモリ", "くさ", ""),
                        new Name(2, "ジュプトル", "くさ", ""),
                        new Name(3, "ジュカイン", "くさ", ""),
                        new Name(4, "アチャモ", "ほのお", ""),
                        new Name(5, "ワカシャモ", "ほのお", "かくとう"),
                        new Name(6, "バシャーモ", "ほのお", "かくとう"),
                        new Name(7, "ミズゴロウ", "みず", ""),
                        new Name(8, "ヌマクロー", "みず", "じめん"),
                        new Name(9, "ラグラージ", "みず", "じめん")
                );
    }

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @Transactional
    void 指定された最初の文字で取得() {
        List<Name> pokemon = pokemonMapper.findByNameStartingWith("ヌ");
        assertThat(pokemon)
                .hasSize(1)
                .contains(
                        new Name(8, "ヌマクロー", "みず", "じめん")
                );
    }

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @Transactional
    void 指定されたIDで取得() {
        Optional<Name> pokemon = pokemonMapper.findById(2);
        assertThat(pokemon)
                .isPresent()
                .hasValue(
                        new Name(2, "ジュプトル", "くさ", "")
                );
    }

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @ExpectedDataSet(value = "datasets/insert-pokemon.yml")
    @Transactional
    void 指定された内容を挿入() {

        //次に付与されるAUTO_INCREMENTによる番号を10にする
        jdbcTemplate.execute("ALTER TABLE pokemon AUTO_INCREMENT = 10");

        Name newName = new Name("ハスボー", "みず", "くさ");
        pokemonMapper.insert(newName);

        Integer newPokemonID = newName.getId();
        Optional<Name> pokemon = pokemonMapper.findById(newPokemonID);
        assertThat(pokemon)
                .isPresent()
                .hasValue(
                        new Name(newPokemonID, "ハスボー", "みず", "くさ")
                );

    }


    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @ExpectedDataSet(value = "datasets/update-pokemon.yml")
    @Transactional
    void 指定されたIDのポケモンを更新() {
        Name updateName = new Name(2, "ハスブレロ", "みず", "くさ");
        pokemonMapper.update(updateName);

        Optional<Name> pokemon = pokemonMapper.findById(2);
        assertThat(pokemon)
                .isPresent()
                .hasValue(
                        new Name(2, "ハスブレロ", "みず", "くさ")
                );
    }

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @ExpectedDataSet(value = "datasets/delete-pokemon.yml")
    @Transactional
    void 指定されたIDのポケモンを削除() {
        pokemonMapper.deleteId(7);

        Optional<Name> pokemon = pokemonMapper.findById(7);
        assertThat(pokemon).isNotPresent();
    }

}
