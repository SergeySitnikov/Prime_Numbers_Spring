package com.netcracker.primenumbers.controllers;

import com.netcracker.primenumbers.domain.UserDetailsImpl;
import com.netcracker.primenumbers.domain.entities.Number;
import com.netcracker.primenumbers.domain.entities.User;
import com.netcracker.primenumbers.domain.models.Pager;
import com.netcracker.primenumbers.services.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private NumberService numberService;


    @GetMapping
    public String getProfile(Model model, Authentication authentication) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        model.addAttribute("user", user.getLogin());
        return "profile/profile";
    }

    @GetMapping("/my")
    public String getMyNumbers(Model model, Authentication authentication,@PageableDefault() Pageable pageable) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Page<Number> firstUserNumber = this.numberService.getFirstNumbersByUser(user, pageable);
        model.addAttribute("numbers", firstUserNumber);
        Pager pager = new Pager(firstUserNumber.getTotalPages(), firstUserNumber.getNumber());
        model.addAttribute("pager", pager);
        return "profile/table";
    }

    @GetMapping("/all")
    public String getAllNumbers(Model model, @PageableDefault()Pageable pageable) {
        Page<Number> allNumbers = this.numberService.getAll(pageable);
        Pager pager = new Pager(allNumbers.getTotalPages(), allNumbers.getNumber());
        model.addAttribute("numbers", allNumbers);
        model.addAttribute("pager", pager);
        return "profile/tableAll";
    }

    @GetMapping("/allMy")
    public String getAllMyNumbers(Model model, @PageableDefault()Pageable pageable) {
        Page<Number> allMyNumbers = this.numberService.getAllByUser(pageable);
        model.addAttribute("numbers", allMyNumbers);
        Pager pager = new Pager(allMyNumbers.getTotalPages(), allMyNumbers.getNumber());
        model.addAttribute("pager", pager);
        return "profile/tableAllMy";
    }

}

