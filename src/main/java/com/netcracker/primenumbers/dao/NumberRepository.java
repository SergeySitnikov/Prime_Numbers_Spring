package com.netcracker.primenumbers.dao;

import com.netcracker.primenumbers.domain.entities.Number;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NumberRepository extends JpaRepository<Number, Long> {
    Optional<Number> findByNumberValue(long number);
}
