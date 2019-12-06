package com.netcracker.primenumbers.forms.validators;

import com.netcracker.primenumbers.forms.NumberCheckForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NumberCheckFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return NumberCheckForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        NumberCheckForm form = (NumberCheckForm)o;
        switch (form.getMethod()) {
            case "miller":
            case "ferma":
            case "solovay":
                break;
            default:errors.rejectValue("method", "invalid.type.method", "Invalid type");
        }
    }
}
