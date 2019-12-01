package com.netcracker.primenumbers.dao;

import com.netcracker.primenumbers.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Optional<User> findByUserId(Long id);

    Page<User> findAll(Pageable pageable);

    int countByUserId(Long id);

    @Transactional
    void deleteByUserId(Long id);


}
