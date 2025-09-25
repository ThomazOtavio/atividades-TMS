package org.example.doubles;

public class NotificationService {
    private final EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendWelcome(String email) {
        emailService.send(email, "Welcome!");
    }
}
