package com.netcracker.primenumbers.controllers;

import com.netcracker.primenumbers.domain.entities.User;
import com.netcracker.primenumbers.domain.models.Pager;
import com.netcracker.primenumbers.exceptions.InvalidLogin;
import com.netcracker.primenumbers.exceptions.UserDoesNotExist;
import com.netcracker.primenumbers.forms.UserChangeForm;
import com.netcracker.primenumbers.forms.validators.UserChangeFormValidator;
import com.netcracker.primenumbers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/adminpanel")
public class AdminPanelUserController {
    private static final int INITIAL_PAGE_SIZE = 10;

    @Autowired
    private UserService userService;


    @Autowired
    private UserChangeFormValidator userValidator;

    @InitBinder("userChangeForm")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(this.userValidator);
    }

    @GetMapping
    public String getUsers(@PageableDefault() Pageable pageable, Model model) {
        pageable = this.isCorrectPage(pageable);
        Page<User> userPage = this.userService.getAllUsers(pageable);
        Pager pager = new Pager(userPage.getTotalPages(), userPage.getNumber());
        model.addAttribute("users", userPage);
        model.addAttribute("pager", pager);
        return "adminPanel/adminPanel";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable String id, @RequestParam(required = false) String delete, Model model, UserChangeForm userChangeForm) {
        try {
            Long idL = Long.parseLong(id);
            if (delete != null) {
                this.userService.deleteUser(idL);
                return "redirect:/adminpanel";
            }
            Optional<User> user = this.userService.getUserById(idL);
            model.addAttribute("u", user);
            model.addAttribute("UserChangeForm", userChangeForm);
            return "adminPanel/user";
        } catch (NumberFormatException ex) {
            return "redirect:/adminpanel";
        }
    }

    @PostMapping("/{id}")
    public String editUser(@PathVariable String id, @Valid @ModelAttribute("userChangeForm") UserChangeForm userChangeForm, BindingResult binding, Model model) {
        try {
            Long idL = Long.parseLong(id);
            Optional<User> user = this.userService.getUserById(idL);
            model.addAttribute("u", user.orElse(null));
            if (binding.hasErrors()) {
                return "adminPanel/user";
            }
            this.userService.changeUser(userChangeForm, idL);
            return "redirect:/adminpanel/users/" + idL;
        } catch (NumberFormatException | UserDoesNotExist | InvalidLogin  ex) {
            return "redirect:/adminpanel";
        }
    }


    private Pageable isCorrectPage(Pageable pageable) {
        if (pageable.getPageSize() != INITIAL_PAGE_SIZE) {
            return PageRequest.of(0, INITIAL_PAGE_SIZE);
        } else {
            return pageable;
        }
    }
}
