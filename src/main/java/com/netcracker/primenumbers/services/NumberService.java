package com.netcracker.primenumbers.services;

import com.netcracker.primenumbers.domain.entities.Number;
import com.netcracker.primenumbers.domain.entities.User;
import com.netcracker.primenumbers.domain.logicOfApp.ResultOfPrime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;

public interface NumberService {
    ResultOfPrime getResult(long number);

    void addNumber(long number, boolean result, User user) throws SQLException;

    Page<Number> getFirstNumbersByUser(User user, Pageable pageable);

    Page<Number> getAll(Pageable pageable);

    Page<Number> getAllByUser(Pageable pageable);

    boolean isPrimeFromNotExistBlock(String method, Long number);



}
