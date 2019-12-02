package com.netcracker.primenumbers.services.impl;

import com.netcracker.primenumbers.dao.NumberRepository;
import com.netcracker.primenumbers.domain.entities.Number;
import com.netcracker.primenumbers.domain.entities.User;
import com.netcracker.primenumbers.domain.logicOfApp.ResultOfPrime;
import com.netcracker.primenumbers.services.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            //this.addUserToNumber(byNumberValue.get());
            if (tmp) return ResultOfPrime.PRIME;
            return ResultOfPrime.NOT_PRIME;
        }
        return ResultOfPrime.NOT_EXIST;
    }

    @Override
    public void addNumber(long number, boolean result, User user) {
        Number n = new Number();
        n.setNumberValue(number);
        n.setPrime(result);
        n.setFirstUser(user);
        this.numberRepository.save(n);
    }

    @Override
    public Page<Number> getFirstNumbersByUser(User user, Pageable pageable) {
        return this.numberRepository.findAllByFirstUser(user, pageable);
    }

    @Override
    public Page<Number> getAll(Pageable pageable) {
        return this.numberRepository.findAll(pageable);
    }

//    private void addUserToNumber(Number number) {
//        User user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//        number.getUsers().add(user);
//        this.numberRepository.save(number);
//    }
}

