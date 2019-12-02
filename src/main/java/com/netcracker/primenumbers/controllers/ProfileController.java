package com.netcracker.primenumbers.controllers;

import com.netcracker.primenumbers.domain.UserDetailsImpl;
import com.netcracker.primenumbers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String getProfile(Model model, Authentication authentication) {
        model.addAttribute("user",((UserDetailsImpl) authentication.getPrincipal()).getUser());
            return "profile";
        }
    }

