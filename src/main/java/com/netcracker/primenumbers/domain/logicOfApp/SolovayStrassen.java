package com.netcracker.primenumbers.domain.logicOfApp;

public class SolovayStrassen extends TestFerma implements SearchPrimeNumbers {

    public SolovayStrassen(long number) {
        this.number = number;
    }

    public SolovayStrassen() {}

    public boolean isPrimeNumber() {
        if (number == 2) {
            return true;
        }
        if (this.initialCheck(number)) {
            for (int i = 0; i < 100; i++) {
                long tmp = (long) (Math.random() * (number - 2) + 2);
                if (greatestCommonDivider(tmp, number) != 1) return false;
                long yacoby = this.algorithmYacoby(tmp, number);
                if (yacoby == -1) {
                    yacoby = number - 1;
                }

                if (pows(tmp, (number- 1) / 2, number) != yacoby) {
                    return false;
                }
            }

        } else {
            return false;
        }
        return true;
    }

    public int algorithmYacoby(long a, long b) {
        if (greatestCommonDivider(a, b) != 1) return 0;
        int r = 1;

        do {
            int t = 0;
            while (a % 2 == 0) {
                t++;
                a /= 2;
            }
            if (t % 2 != 0) {
                t = (int) (b % 8);
                if (t == 3 || t == 5) r = -r;
            }
            if (a % 4 == 3 && b % 4 == 3)
                r = -r;
            long c = a;
            a = b % c;
            b = c;

        } while (a != 0);
        return r;
    }


    public boolean isPrimeNumber(long n) {
        this.number = n;
        return this.isPrimeNumber();
    }
}
