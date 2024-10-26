package com.finalhomework.pokemonservice;

import com.finalhomework.pokemonservice.Excption.NameDuplicateException;
import com.finalhomework.pokemonservice.Excption.PokemonNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokemonServiceTest {

    @InjectMocks
    private PokemonService pokemonService;

    @Mock
    private PokemonMapper pokemonMapper;

    @Test
    public void すべてのポケモンを返す() {
        List<Name> PokemonList = List.of(
                new Name(1, "キモリ", "くさ", ""),
                new Name(2, "ジュプトル", "くさ", ""),
                new Name(3, "ジュカイン", "くさ", ""),
                new Name(4, "アチャモ", "ほのお", ""),
                new Name(5, "ワカシャモ", "ほのお", "かくとう"),
                new Name(6, "バシャーモ", "ほのお", "かくとう"),
                new Name(7, "ミズゴロウ", "みず", ""),
                new Name(8, "ヌマクロー", "みず", "じめん"),
                new Name(9, "ラグラージ", "みず", "じめん"));
        doReturn(PokemonList).when(pokemonMapper).findAll();

        List<Name> actual = pokemonService.getName(null);
        assertThat(actual).isEqualTo(PokemonList);

        verify(pokemonMapper).findAll();
    }

    @Test
    public void 指定した頭文字のポケモンを返す() {
        doReturn(List.of(new Name(6, "バシャーモ", "ほのお", "かくとう"))).when(pokemonMapper).findByNameStartingWith("バ");

        List<Name> actual = pokemonService.getName("バ");
        assertThat(actual).isEqualTo(List.of(new Name(6, "バシャーモ", "ほのお", "かくとう")));

        verify(pokemonMapper).findByNameStartingWith("バ");
    }

    @Test
    public void 指定したIDのポケモンを返す() {
        doReturn(Optional.of(new Name(3, "ジュカイン", "くさ", ""))).when(pokemonMapper).findById(3);

        Name actual = pokemonService.findNameById(3);
        assertThat(actual).isEqualTo(new Name(3, "ジュカイン", "くさ", ""));

        verify(pokemonMapper).findById(3);
    }

    @Test
    public void 指定したIDが存在しない場合は例外をスローする() {
        doReturn(Optional.empty()).when(pokemonMapper).findById(100);

        assertThatThrownBy(() -> pokemonService.findNameById(100)).isInstanceOf(PokemonNotFoundException.class);

        verify(pokemonMapper).findById(100);
    }

    @Test
    public void 新しいポケモンの登録() {
        Name newName = new Name("ポチエナ", "あく", "");

        Name actual = pokemonService.insert("ポチエナ", "あく", "");
        assertThat(actual).isEqualTo(newName);

        verify(pokemonMapper).insert(newName);
    }

    @Test
    public void 同じ名前のポケモンを登録しようとすると例外をスローする() {
        Name newName = new Name("キモリ", "くさ", "");
        doReturn(List.of(newName)).when(pokemonMapper).findAll();

        assertThatThrownBy(() -> pokemonService.insert("キモリ", "くさ", "")).isInstanceOf(NameDuplicateException.class);

        verify(pokemonMapper, never()).insert(newName);
    }

    @Test
    public void 指定したIDのポケモンを更新する() {
        Name updateName = new Name(9, "ジグザグマ", "ノーマル", "");
        doReturn(Optional.of(new Name(9, "ラグラージ", "みず", "じめん"))).when(pokemonMapper).findById(9);

        Name actual = pokemonService.update(9, "ジグザグマ", "ノーマル", "");
        assertThat(actual).isEqualTo(updateName);

        verify(pokemonMapper).update(updateName);
    }

    @Test
    public void 存在しないIDを更新しようとすると例外をスローする() {
        Name updateName = new Name(13, "マッスグマ", "ノーマル", "");

        assertThatThrownBy(() -> pokemonService.update(13, "マッスグマ", "ノーマル", "")).isInstanceOf(PokemonNotFoundException.class);

        verify(pokemonMapper, never()).update(updateName);
    }

    @Test
    public void 指定したIDのポケモンを削除する() {
        doReturn(Optional.of(new Name(5, "ワカシャモ", "ほのお", "かくとう"))).when(pokemonMapper).findById(5);

        pokemonService.delete(5);

        verify(pokemonMapper).deleteId(5);
    }

    @Test
    public void 存在しないIDを削除しようとすると例外をスローする() {

        assertThatThrownBy(() -> pokemonService.delete(100)).isInstanceOf(PokemonNotFoundException.class);

        verify(pokemonMapper, never()).deleteId(100);
    }

}
