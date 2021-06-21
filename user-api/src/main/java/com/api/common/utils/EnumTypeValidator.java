package com.api.common.utils;

import com.api.common.anotation.EnumTypeValid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumTypeValidator implements ConstraintValidator<EnumTypeValid, String> {

    private List<String> types;

    @Override
    public void initialize(EnumTypeValid constraintAnnotation) {
        types = Arrays.stream(
                constraintAnnotation.enumClass().getEnumConstants())
                        .map(type -> type.getName())
                        .collect(Collectors.toList());

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return types.contains(value);
    }
}
