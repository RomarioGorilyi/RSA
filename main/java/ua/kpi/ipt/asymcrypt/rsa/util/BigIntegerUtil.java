package main.java.ua.kpi.ipt.asymcrypt.rsa.util;

import main.java.ua.kpi.ipt.asymcrypt.rsa.exception.NoInverseElementException;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Roman Horilyi
 */
public class BigIntegerUtil {

    /**
     * Generates random {@code BigInteger} number of the specified length.
     *
     * @param length length of the generated X in bytes
     * @return {@code BigInteger} number
     */
    public static BigInteger generateRandomNumber(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randoms = new byte[length];
        secureRandom.nextBytes(randoms);

        return new BigInteger(randoms).abs();
    }

    /**
     * Generates a random {@code BigInteger} number of the specified length between specified MIN and MAX values.
     *
     * @param length length of the {@code BigInteger} random number in bytes
     * @param min bottom limit of a {@code BigInteger} random number
     * @param max upper limit of a {@code BigInteger} random number
     * @return {@code BigInteger} random number
     */
    public static BigInteger generateRandomNumber(int length, BigInteger min, BigInteger max) {
        BigInteger result = generateRandomNumber(length);
        while ((result.compareTo(min) == -1) || (result.compareTo(max) == 1)) {
            result = generateRandomNumber(length);
        }

        return result;
    }

    /**
     * Finds an inverse element of the specified number modulo the specified number.
     *
     * @param number the {@code BigInteger} number whose inverse element is found
     * @param mod the modulus of the congruence
     * @return the {@code BigInteger} inverse element
     * @throws NoInverseElementException if GCD of the specified number and modulus is NOT 1
     */
    public static BigInteger findInverseElement(BigInteger number, BigInteger mod) throws NoInverseElementException {
        BigInteger[] result = computeExtendedEuclidAlgorithm(number, mod);
        if (result[0].compareTo(BigInteger.ONE) == 0) {
            return result[1].mod(mod);
        } else {
            throw new NoInverseElementException(number, mod);
        }
    }

    /**
     * Computes the <a href="https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm">extended Euclid algorithm</a>
     * and calculates an equitation a*x + b*y = gcd(a, b) = d.
     *
     * @return the array {@code [gcd(a, b), x, y]}
     */
    public static BigInteger[] computeExtendedEuclidAlgorithm(BigInteger a, BigInteger b) {
        BigInteger gcd;
        BigInteger x;
        BigInteger y;

        if (b.compareTo(BigInteger.ZERO) == 0) {
            gcd = a;
            x = BigInteger.ONE;
            y = BigInteger.ZERO;
            return new BigInteger[] {gcd, x, y};
        } else {
            BigInteger q;
            BigInteger r;
            BigInteger x1 = BigInteger.ZERO;
            BigInteger x2 = BigInteger.ONE;
            BigInteger y1 = BigInteger.ONE;
            BigInteger y2 = BigInteger.ZERO;

            while (b.compareTo(BigInteger.ZERO) > 0) {
                q = a.divide(b);
                r = a.subtract(q.multiply(b));

                x = x2.subtract(q.multiply(x1));
                y = y2.subtract(q.multiply(y1));

                a = b;
                b = r;

                x2 = x1;
                x1 = x;
                y2 = y1;
                y1 = y;
            }

            return new BigInteger[] {a, x2, y2};
        }
    }
}
