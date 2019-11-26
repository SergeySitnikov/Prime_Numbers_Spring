package com.netcracker.primenumbers.controllers;


import com.netcracker.primenumbers.forms.UserRegistrationForm;
import com.netcracker.primenumbers.forms.validators.UserRegistrationFormValidator;
import com.netcracker.primenumbers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserRegistrationFormValidator validator;

    @Autowired
    private UserService userService;

    @InitBinder("userRegistrationForm")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping
    public String registration(Model model) {
        return "authorization/registration";
    }


    @PostMapping
    public String newUser(@ModelAttribute @Valid UserRegistrationForm userRegistrationForm, BindingResult result) {
        userRegistrationForm.setRole("USER");
        if (result.hasErrors()) {
            return "redirect:/registration";
        }
        try {
            if (this.userService.userWithLoginExists(userRegistrationForm.getLogin())) {
                return "redirect:/registration";
            } else {
                this.userService.createUserFromRegistrationForm(userRegistrationForm);
                return "redirect:/login";
            }
        } catch (SQLException ex) {
            return "redirect:/registration";
        }

    }

}
