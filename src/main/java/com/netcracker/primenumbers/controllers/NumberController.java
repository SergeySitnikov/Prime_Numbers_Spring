package com.netcracker.primenumbers.controllers;

import com.netcracker.primenumbers.domain.logicOfApp.*;
import com.netcracker.primenumbers.services.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/find")
public class NumberController {

    @Autowired
    private NumberService numberService;

    @GetMapping
    public String getMapping() {
        return "find";
    }

    @GetMapping("/{number}")
    public String findNumber(@PathVariable String number, Model model) {
        long numberL;
        try {
            numberL = Long.parseLong(number);
        } catch (NumberFormatException ex) {
            return "redirect:/";
        }
        ResultOfPrime result = numberService.getResult(numberL);
        if (result == ResultOfPrime.PRIME) {
            model.addAttribute("answer", "Your number is prime");
        }
        if (result == ResultOfPrime.NOT_PRIME) {
            model.addAttribute("answer", "Your number isn't prime");
        }
        if (result == ResultOfPrime.NOT_EXIST) {
            MillerRabin millerRabin = new MillerRabin(numberL);
            TestFerma ferma = new TestFerma(numberL);
            SolovayStrassen solovayStrassen = new SolovayStrassen(numberL);
            boolean resultB = false;
            Optional<Boolean> any = Arrays.asList(millerRabin, ferma, solovayStrassen).stream().parallel().map(it -> it.isPrimeNumber()).filter(it -> it).findAny();
            if (any.isPresent()) {
                SearchPrimeNumberByEnumeration enumeration = new SearchPrimeNumberByEnumeration(numberL);
                if (enumeration.isPrimeNumber()) {
                    resultB = true;
                }
            }
            try {
                this.numberService.addNumber(numberL, resultB);
            } catch (SQLException ex) {

            }
            model.addAttribute("answer", resultB ? "Your number is prime" : "Your number isn't prime");
        }


        return "find";
    }
}