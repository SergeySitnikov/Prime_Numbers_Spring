package com.netcracker.primenumbers.domain.logicOfApp;

public class TestFerma extends Methods implements SearchPrimeNumbers {

    public TestFerma(long number) {
        this.number = number;
    }


    public TestFerma() {
    }

    public boolean isPrimeNumber() {
        if (number == 2) {
            return true;
        }
        if (this.initialCheck(number)) {
            for (int i = 0; i < 100; i++) {
                long tmp = (long) (Math.random() * (number - 1) + 1);
                if (this.greatestCommonDivider(tmp, number) != 1) {
                    return false;
                }
                if (this.pows(tmp, number - 1, number) != 1) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;

    }

    public long greatestCommonDivider(long a, long b) { //Create test
        if (b == 0) {
            return a;
        }
        return greatestCommonDivider(b, a % b);
    }

    public long pows(long a, long b, long m) {
        if (b == 0) return 1;
        try {
            if (b % 2 == 0) {
                long t = pows(a, b / 2, m);
                return this.mul(t, t, m); //%m
            }
            return (this.mul(pows(a, b - 1, m), a, m)); //%m
        } catch (StackOverflowError ex) {
            System.out.println("StackOverFlow");
            System.exit(-1);
            return 0;
        }
    }

    public long mul(long a, long b, long m) throws StackOverflowError {
        if (b == 1) return a;
        if (b % 2 == 0) {
            long t = mul(a, b / 2, m);
            return (2 * t) % m;
        }
        return (mul(a, b - 1, m) + a) % m;
    }

    public boolean isPrimeNumber(long n) {
        this.number = n;
        return this.isPrimeNumber();
    }
}
