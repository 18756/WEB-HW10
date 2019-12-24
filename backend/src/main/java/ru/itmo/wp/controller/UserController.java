package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.UserRegisterForm;
import ru.itmo.wp.form.validator.UserRegisterValidator;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.UserService;
import ru.itmo.wp.util.BindingResultUtils;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1/users")
public class UserController extends ApiController {
    private final UserService userService;
    private final JwtService jwtService;
    private final UserRegisterValidator userRegisterValidator;


    public UserController(UserService userService, JwtService jwtService, UserRegisterValidator userRegisterValidator) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.userRegisterValidator = userRegisterValidator;
    }

    @InitBinder("userRegisterForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userRegisterValidator);
    }


    @GetMapping("authorized")
    public User findAuthorized(User user) {
        return user;
    }

    @GetMapping("")
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @PostMapping("register")
    public String register(@RequestBody @Valid UserRegisterForm userRegisterForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }
        User user = userService.register(userRegisterForm.getLogin(), userRegisterForm.getName(), userRegisterForm.getPassword());
        return jwtService.create(user);
    }

}
