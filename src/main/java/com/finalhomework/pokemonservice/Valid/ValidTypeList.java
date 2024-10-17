package com.finalhomework.pokemonservice.Valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class ValidTypeList {

    public enum PokemonType {
        ノーマル,
        ほのお,
        みず,
        でんき,
        くさ,
        こおり,
        かくとう,
        どく,
        じめん,
        ひこう,
        エスパー,
        むし,
        いわ,
        ゴースト,
        ドラゴン,
        あく,
        はがね,
        フェアリー;

        public static PokemonType from(String value) {
            return Optional.of(PokemonType.valueOf(value)).orElseThrow(() -> new IllegalArgumentException("無効なタイプです"));
        }
    }

    public static class ColorValidator implements ConstraintValidator<ValidType, String> {

        @Override
        public void initialize(ValidType constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            ///Type2がない場合を考え空白を許可
            if (value.isEmpty()) {
                return true;
            }
            try {
                PokemonType.from(value);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

    }

}
