package main.java.ua.kpi.ipt.asymcrypt.rsa.prime;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Roman Horilyi
 */
public class PrimeNumber {

    private int value;
    private int bottomBound;
    private int upperBound;

    public PrimeNumber(int bottomBound, int upperBound) {
        this.bottomBound = bottomBound;
        this.upperBound = upperBound;
    }

    public int getValue() {
        return value;
    }

    public void setValue() {
        value = findPrimeNumber();
    }

    private int findX(int bottomBound, int upperBound) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(bottomBound, upperBound + 1);
    }

    private int findM() {
        int m = findX(bottomBound, upperBound);
        while ((m <= upperBound) && (m % 2 == 0)) {
            m++;
        }

        return m <= upperBound ? m : findM();
    }

    private int findPrimeNumber() {
        int m = findM();
        for (int i = 0; i <= (upperBound - m) / 2; i++) {
            int p = m + 2 * i;
            if (isPrimeUsingTrialDivision(p) && isPrimeUsingMillerRabinTest(p)) {
                return p;
            }
        }

        return findPrimeNumber();
    }

    public boolean isPrimeUsingTrialDivision(int number) {
        return number % 2 != 0
                && number % 3 != 0
                && number % 5 != 0
                && number % 7 != 0
                && number % 11 != 0
                && number % 13 != 0;
    }

    public boolean isPrimeUsingMillerRabinTest(int number) {
        boolean isPrime = false;

        int k = 20;

        int d = number - 1;
        int s = 0;
        while (d % 2 == 0) {
            d /= 2;
            s++;
        }

        int counter = 0;

        while (counter++ < k) {
            int x = findX(2, number - 1); // 1 < x < number
            if (findGCD(x, number) == 1) {
               if ((Math.pow(x, d) % number == 1) || (Math.pow(x, d) % number == (number - 1))) {
                   isPrime = true;
               } else {
                   for (int r = 1; r < s; r++) {
                       double xR = Math.pow(x, d * Math.pow(2, r)) % number;

                       if (xR == number - 1) {
                           isPrime = true;
                           break;
                       } else if (xR == 1) {
                           return false;
                       }
                   }
               }
            } else {
                return false;
            }

            if (!isPrime) {
                return false;
            }
        }

        return true;
    }

    private int findGCD(int number1, int number2) {
        if (number2 == 0) {
            return number1;
        } else {
            return findGCD(number2, number1 % number2);
        }
    }
}
