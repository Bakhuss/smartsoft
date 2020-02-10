package ru.bakhuss.smartsoft.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.bakhuss.smartsoft.model.User;
import ru.bakhuss.smartsoft.service.UserService;

/**
 * Validator for {@link ru.bakhuss.smartsoft.model.User},
 * implements {@link Validator}.
 */

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "This field is required.");

        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Such username already exists");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "This field is required.");

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Password don't match.");
        }

    }
}
