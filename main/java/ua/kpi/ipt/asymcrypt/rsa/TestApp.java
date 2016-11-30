package main.java.ua.kpi.ipt.asymcrypt.rsa;

import main.java.ua.kpi.ipt.asymcrypt.rsa.prime.PrimeBigInteger;

/**
 * @author Roman Horilyi
 */
public class TestApp {
    public static void main(String[] args) {
//        PrimeNumber primeNumber = new PrimeNumber(1805, 58484);
//        primeNumber.setValue();
//        System.out.println(primeNumber.getValue());

        PrimeBigInteger primeBigInteger = new PrimeBigInteger();
        primeBigInteger.setValue();
        System.out.println(primeBigInteger.getValue().toString(16));
    }
}
