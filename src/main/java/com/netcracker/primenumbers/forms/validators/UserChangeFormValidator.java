package com.netcracker.primenumbers.forms.validators;

import com.netcracker.primenumbers.forms.UserChangeForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserChangeFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserChangeForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserChangeForm form = (UserChangeForm) o;
        switch (form.getRole()) {
            case "admin":
            case "user":
                break;
                default:errors.rejectValue("role", "invalid.type.role", "Invalid type");
        }
    }
}
