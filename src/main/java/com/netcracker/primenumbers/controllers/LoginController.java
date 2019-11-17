package com.netcracker.primenumbers.controllers;

import com.netcracker.primenumbers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String getMapping() {
        return "authorization/login";
    }

    @RequestMapping("/error")
    public String loginError(Model model, String username) {
        model.addAttribute("username", username);
        return "authorization/login";
    }
}
