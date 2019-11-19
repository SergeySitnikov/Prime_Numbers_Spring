package com.netcracker.primenumbers.controllers;

import com.netcracker.primenumbers.domain.logicOfApp.MillerRabin;
import com.netcracker.primenumbers.domain.logicOfApp.SearchPrimeNumberByEnumeration;
import com.netcracker.primenumbers.domain.logicOfApp.SolovayStrassen;
import com.netcracker.primenumbers.domain.logicOfApp.TestFerma;
import com.netcracker.primenumbers.services.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        Byte result = numberService.getResult(numberL);
        if (result == 0) {
            model.addAttribute("answer", "Your number is prime");
        } else {
            if (result == 1) {
                model.addAttribute("answer", "Your number isn't prime");
            } else {
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
                synchronized (numberService) {
                    this.numberService.addNumber(numberL, resultB);
                }
                model.addAttribute("answer", resultB ? "Your number is prime" : "Your number isn't prime");
            }
        }

        return "find";
    }
}
