package com.netcracker.primenumbers.domain.logicOfApp;

public class SearchPrimeNumberByEnumeration implements SearchPrimeNumbers  {

    private long number;

    public SearchPrimeNumberByEnumeration(long number) {
        this.number = number;
    }

    public SearchPrimeNumberByEnumeration() {}

    public boolean isPrimeNumber() {
        if (number == 2)
            return true;

        if(this.initialCheck(number)) {
            for (int i = 3; i <= Math.sqrt(number); i += 2) {
                if (number % i == 0) {
                    return false;
                }
            }
        } else {
            return false;
        }
            return true;

    }

    public boolean isPrimeNumber(long n) {
        this.number = n;
        return this.isPrimeNumber();
    }
}