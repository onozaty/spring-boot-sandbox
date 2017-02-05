package com.example.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.example.domain.service.UserService;
import com.example.web.UserForm;

import lombok.Data;

@Component
@Data
public class UnusedLoginIdValidator implements ConstraintValidator<UnusedLoginId, UserForm> {

    private static final String field = "loginId";

    private final UserService userService;

    private String message;

    @Override
    public void initialize(UnusedLoginId constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UserForm value, ConstraintValidatorContext context) {

        if (userService.unusedLoginId(value.getLoginId(), value.getId())) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(field).addConstraintViolation();
            return false;
        }
    }
}