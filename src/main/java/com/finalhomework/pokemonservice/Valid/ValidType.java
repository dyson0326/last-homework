package com.finalhomework.pokemonservice.Valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidTypeList.ColorValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface ValidType {
    String message() default "無効なタイプです。";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
