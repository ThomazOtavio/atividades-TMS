package org.example.doubles;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DoublesExamplesTest {

    @Test
    void fake_inMemoryAccountManager_transfer() {
        InMemoryAccountManager repo = new InMemoryAccountManager();
        repo.put(new Account("A", 100));
        repo.put(new Account("B", 0));

        AccountService service = new AccountService(repo);
        service.transfer("A", "B", 60);

        assertEquals(40, repo.findAccountForUser("A").getBalance());
        assertEquals(60, repo.findAccountForUser("B").getBalance());
    }

    @Test
    void spy_emailService_recordsUsage() {
        EmailServiceSpy spy = new EmailServiceSpy();
        NotificationService svc = new NotificationService(spy);

        svc.sendWelcome("a@b.com");

        assertEquals(1, spy.sent.size());
        assertTrue(spy.sent.get(0).contains("Welcome!"));
    }

    @Test
    void dummy_logger_isNotUsed() {
        DummyLogger dummy = new DummyLogger();
        UserService userService = new UserService();
        assertTrue(userService.register("x@y", dummy));
    }
}
