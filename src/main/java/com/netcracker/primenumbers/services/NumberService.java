package com.netcracker.primenumbers.services;

import java.util.Optional;

public interface NumberService {
    Byte getResult(long number);
    void addNumber(long number, boolean result);
}
