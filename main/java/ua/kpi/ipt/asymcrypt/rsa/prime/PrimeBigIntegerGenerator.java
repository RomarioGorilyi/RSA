package main.java.ua.kpi.ipt.asymcrypt.rsa.prime;

import java.math.BigInteger;

import static main.java.ua.kpi.ipt.asymcrypt.rsa.util.RandomBigInteger.generateRandomNumber;

/**
 * @author Roman Horilyi
 */
public class PrimeBigIntegerGenerator {

    /**
     * Generates a prime {@code BigInteger} number that is resistant to attacks.
     *
     * @return a prime number
     */
    public BigInteger generatePrimeNumber() {
        return findAttackResistantPrimeNumber();
    }

    /**
     * Finds X of the specified length.
     *
     * @param length length of the generated X in bytes
     * @return {@code BigInteger} X
     */
    private BigInteger findX(int length) {
        return generateRandomNumber(length);
    }

    /**
     * Finds X of the specified length that is lower than the specified MAX value.
     *
     * @param length length of the generated X in bytes
     * @param max upper limit of a {@code BigInteger} X
     * @return {@code BigInteger} X
     */
    private BigInteger findX(int length, BigInteger max) {
        BigInteger x = generateRandomNumber(length);
        while (x.compareTo(max) >= 0) {
            x = generateRandomNumber(length);
        }

        return x;
    }

    /**
     * Finds M that isn't lower than a newly found X and isn't a paired number.
     *
     * @param length length of a newly generated X in bytes
     * @return {@code BigInteger} M
     */
    private BigInteger findM(int length) {
        BigInteger m = findX(length);
        while (m.remainder(BigInteger.valueOf(2)).intValue() == 0) {
            m = m.add(BigInteger.ONE);
        }

        return m;
    }

    /**
     * Finds a prime number p derived from another prime number
     * because {@code p - 1} has to contain big prime divisors (in the best case, p - 1 = 2p', where p' - prime)
     * in order to resist crypto-analytical attacks on a RSA schema key.
     *
     * @return a prime number
     */
    private BigInteger findAttackResistantPrimeNumber() {
        BigInteger prime = findPrimeNumber();
        BigInteger resultPrime;

        for (int i = 1;; i++) {
            resultPrime = prime.multiply(BigInteger.valueOf(2))
                               .multiply(BigInteger.valueOf(i))
                               .add(BigInteger.ONE);
            if (isPrimeUsingTrialDivision(resultPrime) && isPrimeUsingMillerRabinTest(resultPrime)) {
                return resultPrime;
            }
        }
    }

    /**
     * Finds a prime number checking its primality using
     * {@link #isPrimeUsingTrialDivision(BigInteger)} and {@link #isPrimeUsingMillerRabinTest(BigInteger)} methods.
     *
     * @return a prime number
     */
    private BigInteger findPrimeNumber() {
        BigInteger m = findM(32);
        for (int i = 0; i < 100; i++) {
            BigInteger p = m.add(BigInteger.valueOf(2 * i));
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
    public boolean isPrimeUsingTrialDivision(BigInteger number) {
        return number.mod(BigInteger.valueOf(2)).intValue() != 0
                && number.mod(BigInteger.valueOf(3)).intValue() != 0
                && number.mod(BigInteger.valueOf(5)).intValue() != 0
                && number.mod(BigInteger.valueOf(7)).intValue() != 0
                && number.mod(BigInteger.valueOf(11)).intValue() != 0
                && number.mod(BigInteger.valueOf(13)).intValue() != 0;
    }

    /**
     * Checks if the specified number is prime using
     * (<a href="https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test">Miller–Rabin primality test</a>.
     *
     * @param number a number to check if it is prime
     * @return {@code true} if the specified number is prime
     */
    public boolean isPrimeUsingMillerRabinTest(BigInteger number) {
        boolean isPrime = false;

        int k = 20;

        BigInteger d = number.subtract(BigInteger.ONE);
        int s = 0;
        while (d.mod(BigInteger.valueOf(2)).intValue() == 0) {
            d = d.divide(BigInteger.valueOf(2));
            s++;
        }

        int counter = 0;

        while (counter++ < k) {
            BigInteger x = findX(32, number); // 1 < x < number
            if (x.gcd(number).intValue() == 1) {
                if ((x.modPow(d, number).compareTo(BigInteger.ONE) == 0)
                        || (x.modPow(d, number).compareTo(number.subtract(BigInteger.ONE)) == 0)) {
                    isPrime = true;
                } else {
                    for (int r = 1; r < s; r++) {
                        BigInteger xR = x.modPow(d.multiply(BigInteger.valueOf(2).pow(r)),
                                d.multiply(BigInteger.valueOf(2).pow(r))).add(BigInteger.ONE)
                                         .mod(number);

                        if (xR.compareTo(number.subtract(BigInteger.valueOf(1))) == 0) {
                            isPrime = true;
                            break;
                        } else if (xR.compareTo(BigInteger.ONE) == 0) {
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
}
