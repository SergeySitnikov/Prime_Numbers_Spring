package com.netcracker.primenumbers.controllers;

import com.netcracker.primenumbers.domain.UserDetailsImpl;
import com.netcracker.primenumbers.domain.entities.User;
import com.netcracker.primenumbers.domain.logicOfApp.*;
import com.netcracker.primenumbers.services.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Controller
@RequestMapping("/find")
public class NumberController {

    @Autowired
    private NumberService numberService;

    @GetMapping()
    public String findNumber(@RequestParam(required = false, value = "number") String number, Model model, Authentication auth) {
        if (number == null) {
            model.addAttribute("answer", "");
            return "finder";
        }
        long numberL;
        try {
            numberL = Long.parseLong(number);
        } catch (NumberFormatException ex) {
            return "redirect:/find";
        }
        ResultOfPrime result = numberService.getResult(numberL);
        User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();
        switch(result) {
            case PRIME:
                model.addAttribute("answer", "Your number is prime");
                break;
            case NOT_PRIME:
                model.addAttribute("answer", "Your number isn't prime");
                break;
            case NOT_EXIST:
                MillerRabin millerRabin = new MillerRabin(numberL);
                TestFerma ferma = new TestFerma(numberL);
                SolovayStrassen solovayStrassen = new SolovayStrassen(numberL);
                boolean resultB = false;
                Optional<Boolean> any = Stream.of(millerRabin, ferma, solovayStrassen).parallel().map(it -> it.isPrimeNumber()).filter(it -> it).findAny();
                if (any.isPresent()) {
                    SearchPrimeNumberByEnumeration enumeration = new SearchPrimeNumberByEnumeration(numberL);
                    if (enumeration.isPrimeNumber()) {
                        resultB = true;
                    }
                }
                try {
                    this.numberService.addNumber(numberL, resultB, user);
                } catch (SQLException ex) {

                }
                model.addAttribute("answer", resultB ? "Your number is prime" : "Your number isn't prime");
                break;
        }

        return "finder";
    }
}
