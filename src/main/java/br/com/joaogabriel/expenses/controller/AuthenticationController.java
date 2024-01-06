package br.com.joaogabriel.expenses.controller;

import br.com.joaogabriel.expenses.payload.request.UserPostRequest;
import br.com.joaogabriel.expenses.service.AuthenticationService;
import br.com.joaogabriel.expenses.service.impl.AuthenticationServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/sign-in")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/sign-up")
    public String showRegister(Model model) {
        model.addAttribute("user", new UserPostRequest("", "", "", ""));
        return "register";
    }

    @PostMapping("/sign-up")
    public String register(@ModelAttribute("user") UserPostRequest request, Model model) {
        authenticationService.register(request);
        model.addAttribute("message", true);
        System.out.println(request);
        return "login";
    }
}
