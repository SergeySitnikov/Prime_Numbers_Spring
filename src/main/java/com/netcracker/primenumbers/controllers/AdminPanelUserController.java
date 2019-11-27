package com.netcracker.primenumbers.controllers;

import com.netcracker.primenumbers.domain.entities.User;
import com.netcracker.primenumbers.domain.models.Pager;
import com.netcracker.primenumbers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/adminPanel")
public class AdminPanelUserController {
    private static final int INITIAL_PAGE_SIZE = 10;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getUsers(Pageable pageable, Model model) {
        pageable = this.isCorrectPage(pageable);
        Page<User> userPage = this.userService.getAllUsers(pageable);
        Pager pager = new Pager(userPage.getTotalPages(), userPage.getNumber());
        model.addAttribute("users", userPage);
        model.addAttribute("pager", pager);
        return "adminPanel";
    }

    private Pageable isCorrectPage(Pageable pageable) {
        if (pageable.getPageSize() != INITIAL_PAGE_SIZE) {
            return PageRequest.of(0, INITIAL_PAGE_SIZE);
        } else {
            return pageable;
        }
    }
}
