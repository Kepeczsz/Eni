package com.TaskBoard.TaskBoard.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        validateField(user.getName(), "name", errors, "Name is required.");
        validateField(user.getSurname(), "surname", errors, "Surname is required.");
        validateField(user.getEmail(), "email", errors, "Email is required.");
    }

    private static void validateField(String value, String fieldName, Errors errors, String errorMessage) {
        if (value == null || value.isEmpty()) {
            errors.rejectValue(fieldName, "400", errorMessage);
        }
    }
}
