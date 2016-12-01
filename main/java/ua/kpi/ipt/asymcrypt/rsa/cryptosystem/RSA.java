package main.java.ua.kpi.ipt.asymcrypt.rsa.cryptosystem;

import main.java.ua.kpi.ipt.asymcrypt.rsa.prime.PrimeBigIntegerGenerator;

import java.math.BigInteger;

import static main.java.ua.kpi.ipt.asymcrypt.rsa.util.RandomBigInteger.generateRandomNumber;

/**
 * Created by Roman Horilyi on 30.11.2016.
 */
public class RSA {
    public static void main(String[] args) {
        BigInteger number1 = generateRandomNumber(32);
        BigInteger number2 = generateRandomNumber(32);
        BigInteger e = BigInteger.valueOf(2)
                                 .pow(16)
                                 .add(BigInteger.ONE);

        System.out.println(number1.multiply(number2).toString(16));
        System.out.println(e.toString(16));
    }
}
