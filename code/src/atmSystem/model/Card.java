package model;

import lombok.Getter;

public class Card {
    @Getter
    private final String cardNumber;
    private final String pinHash; // Hashed version of (salt + PIN): for enhanced security
    private final String salt;    // Unique random salt per card
    @Getter
    private final String expirationDate;
    @Getter
    private final String bankName;

    public Card(String cardNumber, String pin, String expirationDate, String bankName) {
        this.cardNumber = cardNumber;
//        this.salt = HashingUtil.generateSalt(); //TODO: implement salt generation
        this.salt = "abcd1234"; // Placeholder salt for demonstration
        this.pinHash = hash(salt + pin);
        this.expirationDate = expirationDate;
        this.bankName = bankName;
    }

    public boolean verifyPin(String enteredPin) {
        String computedHash = hash(salt + enteredPin);
        return computedHash.equals(pinHash);
    }


//    private String hash(String input) {
//        return HashingUtil.sha256(input);
//    }

    private String hash(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
