package main.java.ua.kpi.ipt.asymcrypt.rsa.entity;

import main.java.ua.kpi.ipt.asymcrypt.rsa.prime.PrimeBigIntegerGenerator;
import main.java.ua.kpi.ipt.asymcrypt.rsa.exception.NoInverseElementException;

import java.math.BigInteger;

import static main.java.ua.kpi.ipt.asymcrypt.rsa.util.BigIntegerUtil.findInverseElement;
import static main.java.ua.kpi.ipt.asymcrypt.rsa.util.BigIntegerUtil.generateRandomNumber;

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

    public BigInteger getN() {
        return n;
    }

    public BigInteger getE() {
        return e;
    }

    /**
     * Generates a key pair of prime {@code BigInteger} numbers with the specified length
     * and sets these values to the {@link #p} and {@link #q}.
     *
     * @param length the number of bytes in generated prime numbers
     */
    private void generateKeyPair(int length) {
        PrimeBigIntegerGenerator primeGenerator = new PrimeBigIntegerGenerator();
        p = primeGenerator.generatePrimeNumber(length);
        q = primeGenerator.generatePrimeNumber(length);
    }

    /**
     * Generates {@code n = p * q} and sets this value to the {@link #n}.
     *
     * @param length the number of bytes in generated prime numbers
     */
    public void generateN(int length) {
        generateKeyPair(length);
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
     * Generates an open key e and sets its {@code BigInteger} value to the {@link #e}.
     */
    public void generateE() {
        BigInteger eulerFunction = calculateEulerFunction();
        e = BigInteger.valueOf(65537); // 65537 = 2 ^ 16 + 1
        //e = generateRandomNumber(4, BigInteger.valueOf(2), eulerFunction.subtract(BigInteger.ONE));
        while (e.gcd(eulerFunction).compareTo(BigInteger.ONE) != 0) {
            e = generateRandomNumber(4, BigInteger.valueOf(2), eulerFunction.subtract(BigInteger.ONE));
        }
    }

    /**
     * Calculates a secret key and sets its {@code BigInteger} value to the {@link #d}.
     */
    public void calculateD() {
        try {
            d = findInverseElement(e, calculateEulerFunction());
        } catch (NoInverseElementException e1) {
            System.out.println(e1.getMessage());
        }
    }

    public BigInteger encryptMessage(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e, n);
    }

    public BigInteger decryptMessage(BigInteger encryptedMessage) {
        return encryptedMessage.modPow(d, n);
    }

    /**
     * Signs the specified message.
     *
     * @param message {@code BigInteger} whose signature is created
     * @return {@code BigInteger} signature
     */
    public BigInteger signMessage(BigInteger message) {
        return message.modPow(d, n);
    }

    /**
     * Verifies whether the specified signature is correct and the specified message isn't falsified.
     *
     * @param message {@code BigInteger} with which the signature is verified
     * @param signature {@code BigInteger} which is verified to be correct
     * @param e the public exponent
     * @param n the modulus
     * @return {@code true} if the signature is correct
     */
    public boolean verifySignature(BigInteger message, BigInteger signature, BigInteger e, BigInteger n) {
        return message.compareTo(signature.modPow(e, n)) == 0;
    }
}
