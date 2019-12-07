package com.netcracker.primenumbers.controllers;

import com.netcracker.primenumbers.domain.UserDetailsImpl;
import com.netcracker.primenumbers.domain.entities.User;
import com.netcracker.primenumbers.domain.logicOfApp.*;
import com.netcracker.primenumbers.forms.NumberCheckForm;
import com.netcracker.primenumbers.forms.validators.NumberCheckFormValidator;
import com.netcracker.primenumbers.services.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

@Controller
@RequestMapping("/find")
public class NumberController {

    @Autowired
    private NumberService numberService;

    @Autowired
    private NumberCheckFormValidator formValidator;

    @InitBinder("numberCheckForm")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(this.formValidator);
    }

    @GetMapping
    public String getFile(Model model, NumberCheckForm numberCheckForm) {
        model.addAttribute("answer", "");
        model.addAttribute("numberCheckForm", numberCheckForm);
        return "finder";
    }

    @PostMapping
    public String checkNumber(Model model, @Valid @ModelAttribute("numberCheckForm") NumberCheckForm numberCheckForm, BindingResult binding, Authentication auth) {
        if (binding.hasErrors()) {
            return "redirect:/find";
        }
        Long number;
        try {
            number = Long.parseLong(numberCheckForm.getNumber());
        } catch (NumberFormatException ex) {
            return "redirect:/find";
        }
        if (number < 0) {
            return "redirect:/find";
        }
        ResultOfPrime result = numberService.getResult(number);
        User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        String answerY = "Your number is prime";
        String answerN = "Your number isn't prime";
        switch (result) {
            case PRIME:
                model.addAttribute("answer", answerY);
                break;
            case NOT_PRIME:
                model.addAttribute("answer", answerN);
                break;
            case NOT_EXIST:
               boolean resultB = this.numberService.isPrimeFromNotExistBlock(numberCheckForm.getMethod(), number);
                try {
                    this.numberService.addNumber(number, resultB, user);
                } catch (SQLException ex) {
                    return "redirect:/find";
                }
                model.addAttribute("answer", resultB ? answerY : answerN);
                break;
        }

        return "finder";
    }

}
