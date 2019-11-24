package com.netcracker.primenumbers.services;

import com.netcracker.primenumbers.domain.logicOfApp.ResultOfPrime;

import java.sql.SQLException;

public interface NumberService {
    ResultOfPrime getResult(long number);
    void addNumber(long number, boolean result) throws SQLException;
}