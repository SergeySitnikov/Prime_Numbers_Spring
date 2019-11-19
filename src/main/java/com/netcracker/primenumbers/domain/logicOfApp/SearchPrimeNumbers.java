package com.netcracker.primenumbers.domain.logicOfApp;

public interface SearchPrimeNumbers {
    boolean isPrimeNumber();

    default boolean initialCheck(long n) {
        if (n < 2)
            return false;
        if (n % 2 == 0)
            return false;
        return true;
    }
}
