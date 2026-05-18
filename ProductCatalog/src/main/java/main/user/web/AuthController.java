/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.user.web;

import jakarta.validation.Valid;
import main.user.service.EmailAlreadyExistsException;
import main.user.service.UserRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер страниц регистрации и входа.
 */
@Controller
public class AuthController {

    private final UserRegistrationService userRegistrationService;

    public AuthController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("registerUserRequest", new RegisterUserRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute RegisterUserRequest registerUserRequest,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            userRegistrationService.register(registerUserRequest);
        } catch (EmailAlreadyExistsException exception) {
            model.addAttribute("registrationError", exception.getMessage());
            return "auth/register";
        }

        return "redirect:/login?registered";
    }
}