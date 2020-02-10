package ru.bakhuss.smartsoft.web.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.bakhuss.smartsoft.model.User;
import ru.bakhuss.smartsoft.service.SecurityService;
import ru.bakhuss.smartsoft.service.UserService;
import ru.bakhuss.smartsoft.validator.UserValidator;
import ru.bakhuss.smartsoft.web.controller.UserController;

/**
 * Implementation of {@link UserController}.
 */

@Controller
public class UserControllerImpl implements UserController {
    private final Logger log = LoggerFactory.getLogger(UserControllerImpl.class);

    private final UserService userService;
    private final SecurityService securityService;
    private final UserValidator userValidator;

    @Autowired
    public UserControllerImpl(UserService userService, SecurityService securityService, UserValidator userValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.addUser(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect");
        }
        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        return "login";
    }

    @GetMapping({"/", "/index"})
    public String welcome(Model model) {
        return "index.html";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }
}
