package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.form.UserRegisterForm;
import ru.itmo.wp.service.UserService;

@Component
public class UserRegisterValidator implements Validator {
    private final UserService userService;

    public UserRegisterValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> clazz) {
        return UserRegisterForm.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            UserRegisterForm registerForm = (UserRegisterForm) target;
            if (userService.findByLogin(registerForm.getLogin()) != null) {
                errors.reject("login.login-is-used", "Login is used");
            }
        }
    }
}
