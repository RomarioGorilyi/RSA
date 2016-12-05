package main.java.ua.kpi.ipt.asymcrypt.rsa.cryptosystem;

import main.java.ua.kpi.ipt.asymcrypt.rsa.entity.Transactor;

import java.math.BigInteger;
import java.util.Scanner;

import static main.java.ua.kpi.ipt.asymcrypt.rsa.util.BigIntegerUtil.generateRandomNumber;

/**
 * @author Roman Horilyi
 */
public class RSA {

    private Transactor transactorA;
    private Transactor transactorB;

    public Transactor getTransactorA() {
        return transactorA;
    }

    public Transactor getTransactorB() {
        return transactorB;
    }

    public static void main(String[] args) {
        RSA rsa = new RSA();
        rsa.initTransactor(rsa.getTransactorA());
        rsa.initTransactor(rsa.getTransactorB());

//        rsa.encrypt();
//        rsa.decrypt();
//        rsa.sign();
//        rsa.verify();
    }

    public void initTransactor(Transactor transactor) {
        transactor = new Transactor();
        transactor.generateN(16);
        System.out.println("N: " + transactor.getN().toString(16));
        transactor.generateE();
        System.out.println("E: " + transactor.getE().toString(16));
        transactor.calculateD();
    }

    public void encrypt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message to encrypt: ");
        BigInteger message = new BigInteger(scanner.next(), 16);
        System.out.println("Enter modulus: ");
        BigInteger modulus = new BigInteger(scanner.next(), 16);
        System.out.println("Enter public exponent: ");
        BigInteger exponent = new BigInteger(scanner.next(), 16);

        System.out.println("Encrypted message: " + transactorA.encryptMessage(message, exponent, modulus)
                                                              .toString(16));
    }

    public void decrypt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter encrypted message: ");
        BigInteger encryptedMessage = new BigInteger(scanner.next(), 16);

        System.out.println("Decrypted message: " + transactorA.decryptMessage(encryptedMessage)
                                                              .toString(16));
    }

    public void sign() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message: ");
        BigInteger message = new BigInteger(scanner.next(), 16);

        System.out.println("Signature: " + transactorA.signMessage(message)
                                                      .toString(16));
    }

    public void verify() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message: ");
        BigInteger message = new BigInteger(scanner.next(), 16);
        System.out.println("Enter signature: ");
        BigInteger signature = new BigInteger(scanner.next(), 16);
        System.out.println("Enter modulus: ");
        BigInteger modulus = new BigInteger(scanner.next(), 16);
        System.out.println("Enter public exponent: ");
        BigInteger exponent = new BigInteger(scanner.next(), 16);

        System.out.println("Verification: " + transactorA.verifySignature(message, signature, exponent, modulus));
    }

//    public void sendKey() {
//        BigInteger key = generateRandomNumber(16, BigInteger.ZERO, transactorA.getN());
//        System.out.println("Encrypted key: " + key.modPow(transactorB.getE(), transactorB.getN())
//                                                  .toString(16));
//
//    }
}
