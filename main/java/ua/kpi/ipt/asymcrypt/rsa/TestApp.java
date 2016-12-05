package main.java.ua.kpi.ipt.asymcrypt.rsa;

import main.java.ua.kpi.ipt.asymcrypt.rsa.prime.PrimeBigIntegerGenerator;
import main.java.ua.kpi.ipt.asymcrypt.rsa.prime.PrimeNumberGenerator;

/**
 * @author Roman Horilyi
 */
public class TestApp {
    public static void main(String[] args) {
        PrimeNumberGenerator primeNumberGenerator = new PrimeNumberGenerator(1805, 58484);
        System.out.println(primeNumberGenerator.generateNumber());

        PrimeBigIntegerGenerator primeBigIntegerGenerator = new PrimeBigIntegerGenerator();
        System.out.println(primeBigIntegerGenerator.generatePrimeNumber(32).toString(16));
    }
}
