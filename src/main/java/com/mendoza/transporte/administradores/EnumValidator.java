package com.mendoza.transporte.administradores;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {
    private Set<String> allowedValues;
    private String messageTemplate;

    @Override
    public void initialize(ValidEnum annotation) {
        allowedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
        messageTemplate = annotation.message().replace("{enumValues}", allowedValues.toString());
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) return true;
        if (!allowedValues.contains(value.name())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(messageTemplate)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
