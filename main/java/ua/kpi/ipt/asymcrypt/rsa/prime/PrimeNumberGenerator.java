package main.java.ua.kpi.ipt.asymcrypt.rsa.prime;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Roman Horilyi
 */
public class PrimeNumberGenerator {

    /**
     * The {@code int} value that represents a MIN value of a generated prime number.
     */
    private int bottomBound;

    /**
     * The {@code int} value that represents a MAX value of a generated prime number.
     */
    private int upperBound;

    public PrimeNumberGenerator(int bottomBound, int upperBound) {
        this.bottomBound = bottomBound;
        this.upperBound = upperBound;
    }

    /**
     * Generates a prime int number.
     *
     * @return a prime number
     */
    public int generateNumber() {
        return findPrimeNumber();
    }

    /**
     * Finds X of that is greater than the specified bottom bound and is lower than the specified upper bound.
     *
     * @param bottomBound number that should be lower than a newly found X number
     * @param upperBound number that should be higher than a newly found X number
     * @return {@code int} X
     */
    private int findX(int bottomBound, int upperBound) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(bottomBound, upperBound + 1);
    }

    /**
     * Finds M that isn't lower than a newly found X and isn't a paired number.
     *
     * @return {@code int} M
     */
    private int findM() {
        int m = findX(bottomBound, upperBound);
        while ((m <= upperBound) && (m % 2 == 0)) {
            m++;
        }

        return m <= upperBound ? m : findM();
    }

    /**
     * Finds a prime number checking its primality using
     * {@link #isPrimeUsingTrialDivision(int)} and {@link #isPrimeUsingMillerRabinTest(int)} methods.
     *
     * @return a prime number
     */
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

    /**
     * Checks if the specified number is prime using trial division method
     * (simply dividing by prime numbers such as 2, 3, 5, 7, 11, 13, etc.).
     *
     * @param number a number to check if it is prime
     * @return {@code true} if the specified number is prime
     */
    public boolean isPrimeUsingTrialDivision(int number) {
        return number % 2 != 0
                && number % 3 != 0
                && number % 5 != 0
                && number % 7 != 0
                && number % 11 != 0
                && number % 13 != 0;
    }

    /**
     * Checks if the specified number is prime using
     * (<a href="https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test">Miller–Rabin primality test</a>.
     *
     * @param number a number to check if it is prime
     * @return {@code true} if the specified number is prime
     */
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

    /**
     * Finds great common divisor (GCD) of two specified numbers.
     */
    private int findGCD(int number1, int number2) {
        if (number2 == 0) {
            return number1;
        } else {
            return findGCD(number2, number1 % number2);
        }
    }
}
