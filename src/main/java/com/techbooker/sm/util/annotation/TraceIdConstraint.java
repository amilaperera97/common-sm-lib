package com.techbooker.sm.util.annotation;

import com.techbooker.sm.util.validator.TraceIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = TraceIdValidator.class)
public @interface TraceIdConstraint {

	String message() default "Invalid TraceId";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}