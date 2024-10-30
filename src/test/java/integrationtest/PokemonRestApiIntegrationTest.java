package integrationtest;

import com.finalhomework.pokemonservice.PokemonserviceApplication;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PokemonserviceApplication.class)
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PokemonRestApiIntegrationTest {
    
    @Autowired
    MockMvc mockMvc;

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @Transactional
    void ポケモンを全件取得() throws Exception {
        String response = mockMvc.perform(get("/names"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                            [
                                {
                                    "id": 1,
                                    "name": "キモリ",
                                    "type1": "くさ",
                                    "type2": ""
                                },
                                 {
                                    "id": 2,
                                    "name": "ジュプトル",
                                    "type1": "くさ",
                                    "type2": ""
                                },
                                {
                                    "id": 3,
                                    "name": "ジュカイン",
                                    "type1": "くさ",
                                    "type2": ""
                                },
                                 {
                                    "id": 4,
                                    "name": "アチャモ",
                                    "type1": "ほのお",
                                    "type2": ""
                                },
                                {
                                    "id": 5,
                                    "name": "ワカシャモ",
                                    "type1": "ほのお",
                                    "type2": "かくとう"
                                },
                                 {
                                    "id": 6,
                                    "name": "バシャーモ",
                                    "type1": "ほのお",
                                    "type2": "かくとう"
                                },
                                {
                                    "id": 7,
                                    "name": "ミズゴロウ",
                                    "type1": "みず",
                                    "type2": ""
                                },
                                 {
                                    "id": 8,
                                    "name": "ヌマクロー",
                                    "type1": "みず",
                                    "type2": "じめん"
                                },
                                 {
                                    "id": 9,
                                    "name": "ラグラージ",
                                    "type1": "みず",
                                    "type2": "じめん"
                                }
                            ]
                        """,
                response, JSONCompareMode.STRICT
        );
    }

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @Transactional
    void 指定した最初の文字のポケモンを取得() throws Exception {
        String response = mockMvc.perform(get("/names")
                        .param("startsWith", "ラ"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals(
                """
                            [
                                {
                                    "id": 9,
                                    "name": "ラグラージ",
                                    "type1": "みず",
                                    "type2": "じめん"
                                }
                            ]
                        """,
                response, JSONCompareMode.STRICT
        );
    }

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @Transactional
    void 指定したIDのポケモンを取得() throws Exception {
        String response = mockMvc.perform(get("/names/5"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals(
                """
                         {
                            "id": 5,
                            "name": "ワカシャモ",
                            "type1": "ほのお",
                            "type2": "かくとう"
                         }
                        """,
                response, JSONCompareMode.STRICT
        );
    }

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @Transactional
    void 指定したIDが存在しない場合は例外をスローする() throws Exception {
        mockMvc.perform(get("/names/100"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("pokemon not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/names/100"));

    }

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @ExpectedDataSet(value = "datasets/insert-pokemon.yml")
    @Transactional
    void 指定されたデータを登録() throws Exception {
        mockMvc.perform(post("/names")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "name": "ハスボー",
                                        "type1": "みず",
                                        "type2": "くさ"
                                    }
                                """))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .json("""
                                    {
                                        "message": "pokemon created"
                                    }
                                """));
    }

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @Transactional
    void 同じ名前を登録しようとした場合は例外をスローする() throws Exception {
        mockMvc.perform(post("/names")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 1,
                                    "name": "キモリ",
                                    "type1": "くさ",
                                    "type2": ""
                                },
                                """))
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(HttpStatus.CONFLICT.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("キモリは既に存在しています"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/names"));

    }

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @Transactional
    void 名前とtype1が空白で登録しようとした場合は例外をスローする() throws Exception {
        mockMvc.perform(post("/names")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "name": "",
                                        "type1": "",
                                        "type2": ""
                                    }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("validation error"));

    }

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @ExpectedDataSet(value = "datasets/update-pokemon.yml")
    @Transactional
    void 指定されたIDのポケモンを更新() throws Exception {
        mockMvc.perform(patch("/names/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "name": "ハスブレロ",
                                        "type1": "みず",
                                        "type2": "くさ"
                                    }
                                """))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("""
                                     {
                                         "message":"pokemon update"
                                     }
                                """
                        ));
    }

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @Transactional
    void 存在しないIDを更新しようとした場合は例外をスローする() throws Exception {
        mockMvc.perform(patch("/names/{id}", 300)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "name": "ボスゴドラ",
                                        "type1": "はがね",
                                        "type2": "いわ"
                                    }
                                """))
                .andExpect(status().isNotFound()) // 404 Not Foundを期待
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("存在しないIDです"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/names/" + 300));

    }

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @ExpectedDataSet(value = "datasets/delete-pokemon.yml")
    @Transactional
    void 指定されたIDのポケモンを削除() throws Exception {
        mockMvc.perform(delete("/names/{id}", 7))
                .andExpect(status().isNoContent());
    }

    @Test
    @DataSet(value = "datasets/pokemon.yml")
    @Transactional
    void 存在しないIDを削除しようとした場合は例外をスローする() throws Exception {
        mockMvc.perform(delete("/names/{id}", 500))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("存在しないIDです"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/names/" + 500));
    }

}
