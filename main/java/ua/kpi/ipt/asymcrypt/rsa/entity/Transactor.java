package main.java.ua.kpi.ipt.asymcrypt.rsa.entity;

import main.java.ua.kpi.ipt.asymcrypt.rsa.prime.PrimeBigIntegerGenerator;

import java.math.BigInteger;

import static main.java.ua.kpi.ipt.asymcrypt.rsa.util.RandomBigInteger.generateRandomNumber;

/**
 * @author Roman Horilyi
 */
public class Transactor {

    /**
     * The first {@code BigInteger} prime number.
     */
    private BigInteger p;

    /**
     * The second {@code BigInteger} prime number.
     */
    private BigInteger q;

    /**
     * The element of an open key (n, e).
     * n = p * q
     */
    private BigInteger n;

    /**
     * The element of an open key (n, e) that is randomly generated (2^16 + 1 is almost always used).
     * @see #generateE()
     */
    private BigInteger e;

    /**
     * The secret key.
     */
    private BigInteger d;

    /**
     * Generates a key pair of prime {@code BigInteger} numbers
     * and sets these values to the {@link #p} and {@link #q}.
     */
    public void generateKeyPair() {
        PrimeBigIntegerGenerator primeGenerator = new PrimeBigIntegerGenerator();
        p = primeGenerator.generatePrimeNumber();
        q = primeGenerator.generatePrimeNumber();
    }

    /**
     * Calculates {@code n = p * q} and sets this value to the {@link #n}.
     */
    public void calucalateN() {
        n = p.multiply(q);
    }

    /**
     * Calculates Euler function of {@code BigInteger} {@link #n}.
     *
     * @return {@code BigInteger} number
     */
    public BigInteger calculateEulerFunction() {
        return (p.subtract(BigInteger.ONE))
                .multiply(q.subtract(BigInteger.ONE));
    }

    /**
     * Generates {@link #e}.
     */
    public void generateE() {
        BigInteger eulerFunction = calculateEulerFunction();
        e = generateRandomNumber(4, BigInteger.valueOf(2), eulerFunction.subtract(BigInteger.ONE));
        while (e.gcd(eulerFunction).compareTo(BigInteger.ONE) != 0) {
            e = generateRandomNumber(4, BigInteger.valueOf(2), eulerFunction.subtract(BigInteger.ONE));
        }
    }

}
