package com.hospitalMgt.patiencemgt.validation.uuidValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.UUID;

public class UuidValidator implements ConstraintValidator<Uuid, UUID> {

    @Override
    public void initialize(Uuid validUuid) { }

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext cxt) {
        // the regex
        String regex = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        return uuid.toString().matches(regex);
    }
}
