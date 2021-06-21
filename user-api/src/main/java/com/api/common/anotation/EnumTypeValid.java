package com.api.common.anotation;

import com.api.common.type.EnumInterface;
import com.api.common.utils.EnumTypeValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = {EnumTypeValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumTypeValid {
    String message() default "올바른 type을 입력해주세요";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends EnumInterface> enumClass();

}
