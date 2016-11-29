package ua.kpi.ipt.asymcrypt.rsa.prime;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Roman Horilyi on 20.11.2016.
 */
public class PrimeBigInteger {

    private BigInteger value;

    public BigInteger getValue() {
        return value;
    }

    public void setValue() {
        value = findPrimeValue();
    }

    /**
     * Finds X of the specified length.
     *
     * @param length length of the generated X in bytes
     * @return {@code BigInteger} X
     */
    private BigInteger findX(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randoms = new byte[length];
        secureRandom.nextBytes(randoms);

        return new BigInteger(randoms).abs();
    }

    /**
     * Finds X of the specified length that is lower than the specified {@code BigInteger} number.
     *
     * @param length length of the generated X in bytes
     * @param upperBound {@code BigInteger} number that should be higher than a newly found X number.
     * @return {@code BigInteger} X
     */
    private BigInteger findX(int length, BigInteger upperBound) {
        BigInteger x = findX(length);
        while (x.compareTo(upperBound) >= 0) {
            x = findX(length);
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
            m = m.add(BigInteger.valueOf(1));
        }

        return m;
    }

    private BigInteger findPrimeValue() {
        BigInteger m = findM(32);
        for (int i = 0; i < 100; i++) {
            BigInteger p = m.add(BigInteger.valueOf(2 * i));
            if (isPrimeUsingTrialDivision(p) && isPrimeUsingMillerRabinTest(p)) {
                return p;
            }
        }

        return findPrimeValue();
    }

    public boolean isPrimeUsingTrialDivision(BigInteger number) {
        return number.mod(BigInteger.valueOf(2)).intValue() != 0
                && number.mod(BigInteger.valueOf(3)).intValue() != 0
                && number.mod(BigInteger.valueOf(5)).intValue() != 0
                && number.mod(BigInteger.valueOf(7)).intValue() != 0
                && number.mod(BigInteger.valueOf(11)).intValue() != 0
                && number.mod(BigInteger.valueOf(13)).intValue() != 0;
    }

    public boolean isPrimeUsingMillerRabinTest(BigInteger number) {
        boolean isPrime = false;

        int k = 20;

        BigInteger d = number.subtract(BigInteger.valueOf(1));
        int s = 0;
        while (d.mod(BigInteger.valueOf(2)).intValue() == 0) {
            d = d.divide(BigInteger.valueOf(2));
            s++;
        }

        int counter = 0;

        while (counter++ < k) {
            BigInteger x = findX(32, number); // 1 < x < number
            if (x.gcd(number).intValue() == 1) {
                if ((x.modPow(d, number).compareTo(BigInteger.valueOf(1)) == 0)
                        || (x.modPow(d, number).compareTo(number.subtract(BigInteger.valueOf(1))) == 0)) {
                    isPrime = true;
                } else {
                    for (int r = 1; r < s; r++) {
                        BigInteger xR = x.modPow(d.multiply(BigInteger.valueOf(2).pow(r)),
                                d.multiply(BigInteger.valueOf(2).pow(r))).add(BigInteger.valueOf(1))
                                         .mod(number);

                        if (xR.compareTo(number.subtract(BigInteger.valueOf(1))) == 0) {
                            isPrime = true;
                            break;
                        } else if (xR.compareTo(BigInteger.valueOf(1)) == 0) {
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
