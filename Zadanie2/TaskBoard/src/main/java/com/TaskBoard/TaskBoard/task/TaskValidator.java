package com.TaskBoard.TaskBoard.task;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class TaskValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Task.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Task task = (Task) target;
        validateField(task.getDescription(), "description", errors, "description is required.");
        validateField(task.getTitle(), "title", errors, "title is required.");
        validateField(String.valueOf(task.getDueTo()), "dueTo", errors, "dueTo is required.");
        validateField(String.valueOf(task.getTaskStatus()), "task status", errors, "task status is required.");

    }

    private static void validateField(String value, String fieldName, Errors errors, String errorMessage) {
        if (value == null || value.isEmpty()) {
            errors.rejectValue(fieldName, "400", errorMessage);
        }
    }
}
