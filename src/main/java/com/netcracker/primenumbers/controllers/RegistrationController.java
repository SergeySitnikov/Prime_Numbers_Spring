package com.netcracker.primenumbers.controllers;


import com.netcracker.primenumbers.forms.UserRegistrationForm;
import com.netcracker.primenumbers.forms.responses.JsonResponse;
import com.netcracker.primenumbers.forms.validators.UserRegistrationFormValidator;
import com.netcracker.primenumbers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

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

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public JsonResponse newUser(@ModelAttribute @Valid UserRegistrationForm userRegistrationForm, BindingResult result) {
        JsonResponse jsonResponse = new JsonResponse();
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            jsonResponse.setValidated(false);
            jsonResponse.setErrorMessages(errors);
        } else {
            this.userService.createUserFromRegistrationForm(userRegistrationForm);
            jsonResponse.setValidated(true);
        }
        return jsonResponse;
    }

}
