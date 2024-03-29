package com.netcracker.primenumbers.services.impl;

import com.netcracker.primenumbers.dao.RoleRepository;
import com.netcracker.primenumbers.dao.UserRepository;
import com.netcracker.primenumbers.domain.UserDetailsImpl;
import com.netcracker.primenumbers.domain.entities.Role;
import com.netcracker.primenumbers.domain.entities.User;
import com.netcracker.primenumbers.exceptions.InvalidLogin;
import com.netcracker.primenumbers.exceptions.UserDoesNotExist;
import com.netcracker.primenumbers.forms.UserChangeForm;
import com.netcracker.primenumbers.forms.UserRegistrationForm;
import com.netcracker.primenumbers.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getUserByLogin(String login) {
        return this.userRepository.findByLogin(login);
    }

    @Override
    public Optional<User> getUserFromAuthentication(Authentication authentication) {
        return this.getUserById(((UserDetailsImpl) authentication.getPrincipal()).getId());
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return this.userRepository.findByUserId(id);
    }

    @Override
    public Boolean userWithLoginExists(String login) {
        return this.getUserByLogin(login).isPresent();
    }

    @Override
    public void createUserFromRegistrationForm(UserRegistrationForm userForm) throws SQLException {
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(this.roleRepository.findByRole("USER"));
        user.setRoles(roles);
        this.userRepository.save(user);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public void deleteUser(Long id) {
        if (this.userRepository.countByUserId(id) != 0) {
            this.userRepository.deleteByUserId(id);
        }
    }

    @Override
    public void changeUser(UserChangeForm form, long id) throws UserDoesNotExist, InvalidLogin {
        Optional<User> optionalUser = this.getUserById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.getLogin().equals(form.getLogin()) && this.userWithLoginExists(form.getLogin())) {
                throw new InvalidLogin();
            }
            user.setLogin(form.getLogin());
            Set<Role> roles = new HashSet<>();
            roles.add(this.roleRepository.findByRole("USER"));
            if (form.getRole().equals("admin")) roles.add(this.roleRepository.findByRole("ADMIN"));
            user.setRoles(roles);
            this.userRepository.save(user);
        } else throw new UserDoesNotExist();
    }
}
