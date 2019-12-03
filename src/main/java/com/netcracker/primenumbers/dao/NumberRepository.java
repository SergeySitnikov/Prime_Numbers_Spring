package com.netcracker.primenumbers.dao;

import com.netcracker.primenumbers.domain.entities.Number;
import com.netcracker.primenumbers.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NumberRepository extends JpaRepository<Number, Long> {
    Optional<Number> findByNumberValue(long number);

    Page<Number> findAllByFirstUser(User user, Pageable pageable);

    Page<Number> findAll(Pageable pageable);

    @Query(nativeQuery = true, value = "select * from number join number_user on " +
            "number.number_id = number_user.number_id where user_id = ?1")
    Page<Number> findAllByUserId(Pageable pageable, long id);
}

