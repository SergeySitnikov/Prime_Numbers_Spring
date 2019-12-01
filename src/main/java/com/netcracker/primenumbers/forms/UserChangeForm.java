package com.netcracker.primenumbers.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserChangeForm {
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]{1,15}")
    private String login;

    @NotBlank
    private String role;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
