package main.java.ua.kpi.ipt.asymcrypt.rsa.cryptosystem;

import main.java.ua.kpi.ipt.asymcrypt.rsa.entity.Party;

import java.math.BigInteger;
import java.util.Scanner;

import static main.java.ua.kpi.ipt.asymcrypt.rsa.util.BigIntegerUtil.generateRandomNumber;

/**
 * @author Roman Horilyi
 */
public class RsaSimulator { // TODO create View and Controller, and remove this shit !!!

    public static void main(String[] args) {
        RsaSimulator rsaSimulator = new RsaSimulator();

        Party partyA = rsaSimulator.createParty();
//        Party partyB = rsaSimulator.createParty();

//        rsaSimulator.encrypt(party);
//        rsaSimulator.decrypt(party);
//        rsaSimulator.sign(party);
//        rsaSimulator.verify(party);
        rsaSimulator.sendKey(partyA);
//        rsaSimulator.receiveKey(partyA);

//        rsaSimulator.simulateRsaSchema();
    }

    /**
     * Creates a party and initializes it.
     *
     * @return initialized transactor
     */
    public Party createParty() {
        Party party = new Party();

        party.generateN(32);
        System.out.println("n: " + party.getN().toString(16));
        party.generateE();
        System.out.println("e: " + party.getE().toString(16));
        party.calculateD();

        return party;
    }

    public void encrypt(Party party) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message to encrypt: ");
        BigInteger message = new BigInteger(scanner.next(), 16);
        System.out.println("Enter modulus: ");
        BigInteger modulus = new BigInteger(scanner.next(), 16);
        System.out.println("Enter public exponent: ");
        BigInteger exponent = new BigInteger(scanner.next(), 16);

        System.out.println("Encrypted message: " + party.encryptMessage(message, exponent, modulus)
                                                        .toString(16));
    }

    public void decrypt(Party party) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter encrypted message: ");
        BigInteger encryptedMessage = new BigInteger(scanner.next(), 16);

        System.out.println("Decrypted message: " + party.decryptMessage(encryptedMessage)
                                                        .toString(16));
    }

    public void sign(Party party) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message: ");
        BigInteger message = new BigInteger(scanner.next(), 16);

        System.out.println("Signature: " + party.signMessage(message)
                                                .toString(16));
    }

    public void verify(Party party) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message: ");
        BigInteger message = new BigInteger(scanner.next(), 16);
        System.out.println("Enter signature: ");
        BigInteger signature = new BigInteger(scanner.next(), 16);
        System.out.println("Enter modulus: ");
        BigInteger modulus = new BigInteger(scanner.next(), 16);
        System.out.println("Enter public exponent: ");
        BigInteger exponent = new BigInteger(scanner.next(), 16);

        System.out.println("Verification: " + party.verifySignature(message, signature, exponent, modulus));
    }

    public void sendKey(Party party) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter secret key: ");
        BigInteger key = new BigInteger(scanner.next(), 16);
        System.out.println("Enter public exponent: ");
        BigInteger exponent = new BigInteger(scanner.next(), 16);
        System.out.println("Enter modulus: ");
        BigInteger modulus = new BigInteger(scanner.next(), 16);

        if (party.getN().compareTo(modulus) == 1) {
            System.out.println("Error! Sender modulus must be less that receiver's one." +
                    "Please, enter receiver's modulus again.");
        }

        BigInteger[] keyAndSignature = party.sendKey(key, exponent, modulus);
        System.out.println("Encrypted key: " + keyAndSignature[0].toString(16));
        System.out.println("Signature: " + keyAndSignature[1].toString(16));
    }

    public void receiveKey(Party party) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter encrypted key: ");
        BigInteger key = new BigInteger(scanner.next(), 16);
        System.out.println("Enter signature: ");
        BigInteger signature = new BigInteger(scanner.next(), 16);
        System.out.println("Enter public exponent: ");
        BigInteger exponent = new BigInteger(scanner.next(), 16);
        System.out.println("Enter modulus: ");
        BigInteger modulus = new BigInteger(scanner.next(), 16);

        System.out.println("Decrypted key: " + party.receiveKey(key, signature, exponent, modulus)
                                                    .toString(16));
    }

    public void simulateRsaSchema() {
        System.out.println("Party A:");
        Party partyA = createParty();
        System.out.println("Party B:");
        Party partyB = createParty();
        while (partyA.getN().compareTo(partyB.getN()) == 1) {
            partyB = createParty();
        }

        BigInteger message = generateRandomNumber(32);
        System.out.println("Message: " + message.toString(16));

        BigInteger encryptedMessage = partyA.encryptMessage(message, partyB.getE(), partyB.getN());
        System.out.println("Encrypted message: " + encryptedMessage.toString(16));

        BigInteger decryptedMessage = partyB.decryptMessage(encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage.toString(16));

        if (decryptedMessage.compareTo(message) == 0) {
            System.out.println("Decrypted message is equal to the Message: true!");
        } else {
            System.out.println("Decrypted message is equal to the Message: false!");
        }

        BigInteger signature = partyA.signMessage(message);
        System.out.println("\nSignature: " + signature.toString(16));

        boolean isVerified = partyB.verifySignature(message, signature, partyA.getE(), partyA.getN());
        System.out.println("Verification: " + isVerified);
    }
}
