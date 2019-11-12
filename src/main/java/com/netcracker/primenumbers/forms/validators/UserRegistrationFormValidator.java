package com.netcracker.primenumbers.forms.validators;

import com.netcracker.primenumbers.forms.UserRegistrationForm;
import com.netcracker.primenumbers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserRegistrationFormValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegistrationForm.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        UserRegistrationForm form = (UserRegistrationForm)obj;
        if (this.userService.userWithLoginExists(form.getLogin())) {
            errors.rejectValue("login", "enter.login.exists", "User with login already exists");
        }
    }
}
