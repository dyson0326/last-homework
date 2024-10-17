package com.finalhomework.pokemonservice;

import com.finalhomework.pokemonservice.Valid.ValidType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class NameRequest {

    @NotBlank(message = "値を入力してください")
    @Pattern(regexp = "^[ァ-ヶー]*$", message = "全角カタカナで入力してください")
    private String name;

    @NotBlank(message = "値を入力してください")
    @ValidType
    private String type1;

    @ValidType
    private String type2;

    public NameRequest(String name, String type1, String type2) {
        this.name = name;
        this.type1 = type1;
        this.type2 = type2;
    }

    public String getName() {
        return name;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

}
