package com.netcracker.primenumbers.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/access-denied")
    public String access() {
        return "access-denied";
    }
}
