package main.java.ua.kpi.ipt.asymcrypt.rsa.cryptosystem;

import main.java.ua.kpi.ipt.asymcrypt.rsa.entity.Transactor;

import java.math.BigInteger;
import java.util.Scanner;

import static main.java.ua.kpi.ipt.asymcrypt.rsa.util.BigIntegerUtil.generateRandomNumber;

/**
 * @author Roman Horilyi
 */
public class RsaSimulator {

    public static void main(String[] args) {
        RsaSimulator rsaSimulator = new RsaSimulator();

        Transactor transactorA = rsaSimulator.createTransactor();
        Transactor transactorB = rsaSimulator.createTransactor();

//        rsaSimulator.encrypt(transactor);
//        rsaSimulator.decrypt(transactor);
//        rsaSimulator.sign(transactor);
//        rsaSimulator.verify(transactor);
        rsaSimulator.sendKey(transactorA);
        rsaSimulator.receiveKey(transactorB);

//        rsaSimulator.simulateRsaSchema();
    }

    /**
     * Creates a transactor and initializes it.
     *
     * @return initialized transactor
     */
    public Transactor createTransactor() {
        Transactor transactor = new Transactor();

        transactor.generateN(32);
        System.out.println("n: " + transactor.getN().toString(16));
        transactor.generateE();
        System.out.println("e: " + transactor.getE().toString(16));
        transactor.calculateD();

        return transactor;
    }

    public void encrypt(Transactor transactor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message to encrypt: ");
        BigInteger message = new BigInteger(scanner.next(), 16);
        System.out.println("Enter modulus: ");
        BigInteger modulus = new BigInteger(scanner.next(), 16);
        System.out.println("Enter public exponent: ");
        BigInteger exponent = new BigInteger(scanner.next(), 16);

        System.out.println("Encrypted message: " + transactor.encryptMessage(message, exponent, modulus)
                                                              .toString(16));
    }

    public void decrypt(Transactor transactor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter encrypted message: ");
        BigInteger encryptedMessage = new BigInteger(scanner.next(), 16);

        System.out.println("Decrypted message: " + transactor.decryptMessage(encryptedMessage)
                                                              .toString(16));
    }

    public void sign(Transactor transactor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message: ");
        BigInteger message = new BigInteger(scanner.next(), 16);

        System.out.println("Signature: " + transactor.signMessage(message)
                                                      .toString(16));
    }

    public void verify(Transactor transactor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message: ");
        BigInteger message = new BigInteger(scanner.next(), 16);
        System.out.println("Enter signature: ");
        BigInteger signature = new BigInteger(scanner.next(), 16);
        System.out.println("Enter modulus: ");
        BigInteger modulus = new BigInteger(scanner.next(), 16);
        System.out.println("Enter public exponent: ");
        BigInteger exponent = new BigInteger(scanner.next(), 16);

        System.out.println("Verification: " + transactor.verifySignature(message, signature, exponent, modulus));
    }

    public void sendKey(Transactor transactor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter secret key: ");
        BigInteger key = new BigInteger(scanner.next(), 16);
        System.out.println("Enter public exponent: ");
        BigInteger exponent = new BigInteger(scanner.next(), 16);
        System.out.println("Enter modulus: ");
        BigInteger modulus = new BigInteger(scanner.next(), 16);

        BigInteger[] keyAndSignature = transactor.sendKey(key, exponent, modulus);
        System.out.println("Encrypted key: " + keyAndSignature[0].toString(16));
        System.out.println("Signature: " + keyAndSignature[1].toString(16));
    }

    public void receiveKey(Transactor transactor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter encrypted key: ");
        BigInteger key = new BigInteger(scanner.next(), 16);
        System.out.println("Enter signature: ");
        BigInteger signature = new BigInteger(scanner.next(), 16);
        System.out.println("Enter public exponent: ");
        BigInteger exponent = new BigInteger(scanner.next(), 16);
        System.out.println("Enter modulus: ");
        BigInteger modulus = new BigInteger(scanner.next(), 16);

        System.out.println("Decrypted key: " + transactor.receiveKey(key, signature, exponent, modulus)
                                                         .toString(16));
    }

    public void simulateRsaSchema() {
        System.out.println("Transactor A:");
        Transactor transactorA = createTransactor();
        System.out.println("Transactor B:");
        Transactor transactorB = createTransactor();
        while (transactorA.getN().compareTo(transactorB.getN()) == 1) {
            transactorB = createTransactor();
        }

        BigInteger message = generateRandomNumber(32);
        System.out.println("Message: " + message.toString(16));

        BigInteger encryptedMessage = transactorA.encryptMessage(message, transactorB.getE(), transactorB.getN());
        System.out.println("Encrypted message: " + encryptedMessage.toString(16));

        BigInteger decryptedMessage = transactorB.decryptMessage(encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage.toString(16));

        if (decryptedMessage.compareTo(message) == 0) {
            System.out.println("Decrypted message is equal to the Message: true!");
        } else {
            System.out.println("Decrypted message is equal to the Message: false!");
        }

        BigInteger signature = transactorA.signMessage(message);
        System.out.println("\nSignature: " + signature.toString(16));

        boolean isVerified = transactorB.verifySignature(message, signature, transactorA.getE(), transactorA.getN());
        System.out.println("Verification: " + isVerified);
    }
}
