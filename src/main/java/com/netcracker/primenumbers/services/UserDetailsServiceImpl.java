package com.netcracker.primenumbers.services;

import com.netcracker.primenumbers.dao.UserRepository;
import com.netcracker.primenumbers.domain.UserDetailsImpl;
import com.netcracker.primenumbers.domain.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> optionalUser = this.userRepository.findByLogin(s);
        optionalUser.orElseThrow(() -> new UsernameNotFoundException("Login: " + s + "not found"));
        return optionalUser.map(UserDetailsImpl::new).get();
    }
}
