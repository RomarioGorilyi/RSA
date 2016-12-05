package main.java.ua.kpi.ipt.asymcrypt.rsa.exception;

import java.math.BigInteger;

/**
 * @author Roman Horilyi
 */
public class NoInverseElementException extends Exception {
    public NoInverseElementException(BigInteger number, BigInteger mod) {
        super("There is no inverse element of " + number.toString() + " modulo " + mod);
    }
}
