package com.vidaplus.sghss.model;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String raw = "123456";
        String hash = encoder.encode(raw);
        System.out.println("Hash gerado: " + hash);
        System.out.println("Confere: " + encoder.matches("123456", hash));
    }
}