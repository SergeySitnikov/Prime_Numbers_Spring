package com.netcracker.primenumbers.controllers;

import com.netcracker.primenumbers.domain.UserDetailsImpl;
import com.netcracker.primenumbers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalAdviceController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addUser(Authentication authentication, Model model) {
        if (authentication != null) {
            this.userService.getUserFromAuthentication(authentication).ifPresent(user -> {
                UserDetails userDetails = new UserDetailsImpl(user);
                Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
                model.addAttribute("user", user);
            });
        }
    }
}
