package com.netcracker.primenumbers.services.impl;

import com.netcracker.primenumbers.dao.NumberRepository;
import com.netcracker.primenumbers.domain.entities.Number;
import com.netcracker.primenumbers.domain.logicOfApp.ResultOfPrime;
import com.netcracker.primenumbers.services.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NumberServiceImpl implements NumberService {

    @Autowired
    private NumberRepository numberRepository;

    @Override
    public ResultOfPrime getResult(long number) {
        Optional<Number> byNumberValue = numberRepository.findByNumberValue(number);
        if (byNumberValue.isPresent()) {
            boolean tmp = byNumberValue.get().isPrime();
            if (tmp) return ResultOfPrime.PRIME;
            return ResultOfPrime.NOT_PRIME;
        }
        return ResultOfPrime.NOT_EXIST;
    }

    @Override
    public void addNumber(long number, boolean result) {
        Number n = new Number();
        n.setNumberValue(number);
        n.setPrime(result);
        this.numberRepository.save(n);
    }
}

