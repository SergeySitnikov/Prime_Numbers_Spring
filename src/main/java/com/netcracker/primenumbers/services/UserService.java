package com.netcracker.primenumbers.services;


import com.netcracker.primenumbers.domain.entities.User;
import com.netcracker.primenumbers.forms.UserRegistrationForm;
import org.springframework.security.core.Authentication;

import java.sql.SQLException;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByLogin(String login);

    Optional<User> getUserFromAuthentication(Authentication authentication);

    Optional<User> getUserById(Long id);

    Boolean userWithLoginExists(String login);

    void createUserFromRegistrationForm(UserRegistrationForm userForm) throws SQLException;



}
