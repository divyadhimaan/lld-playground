package service;

public class AuthenticationService {

    Boolean authenticate(String cardNumber, String pin) {
        return "1234".equals(pin);
    }
}
