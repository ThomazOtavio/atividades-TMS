package org.example.doubles;

import java.util.ArrayList;
import java.util.List;

// Test Spy: registra os envios para asserts
public class EmailServiceSpy implements EmailService {
    public final List<String> sent = new ArrayList<>();
    @Override
    public void send(String to, String message) {
        sent.add(to + ":" + message);
    }
}
