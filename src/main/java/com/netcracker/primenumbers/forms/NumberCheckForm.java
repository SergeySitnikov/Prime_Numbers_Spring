package com.netcracker.primenumbers.forms;

import javax.validation.constraints.NotBlank;

public class NumberCheckForm {

    @NotBlank
    private String number;

    @NotBlank
    private String method;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
