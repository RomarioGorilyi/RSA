package main.java.ua.kpi.ipt.asymcrypt.rsa.util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Roman Horilyi
 */
public class RandomBigInteger {

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
}
