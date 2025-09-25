package org.example.doubles;

public class UserService {
    // Método que aceita um logger (Dummy) mas não o usa neste fluxo de teste
    public boolean register(String email, Logger logger) {
        // Fluxo simplificado: retorna sempre true
        return email != null && !email.isBlank();
    }
}
