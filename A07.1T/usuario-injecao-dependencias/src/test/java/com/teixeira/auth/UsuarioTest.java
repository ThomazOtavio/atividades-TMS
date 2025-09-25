package com.teixeira.auth;

import com.teixeira.auth.util.MutableClock;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teste de Usuario - Injeção de Dependências e Regras de Autenticação")
class UsuarioTest {

    private TestReporter reporter;

    @BeforeEach
    void setup(TestInfo info, TestReporter reporter) {
        this.reporter = reporter;
        reporter.publishEntry("Iniciando teste", info.getDisplayName());
    }

    @Test
    @DisplayName("1) Deve validar campos obrigatórios e formatos de nome/email")
    void validacoesBasicas() {
        PasswordHasher hasher = new MessageDigestPasswordHasher("SHA-256");
        Usuario u = new Usuario(hasher, MutableClock.nowUTC());

        u.setNome("Maria Silva");
        assertEquals("Maria Silva", u.getNome());

        u.setEmail("maria.silva_01@dominio.com");
        assertEquals("maria.silva_01@dominio.com", u.getEmail());

        assertThrows(IllegalArgumentException.class, () -> u.setNome("João"));
        assertThrows(IllegalArgumentException.class, () -> u.setNome("Ana-123"));
        assertThrows(IllegalArgumentException.class, () -> u.setNome("   "));

        assertThrows(IllegalArgumentException.class, () -> u.setEmail("user@dominio"));
        assertThrows(IllegalArgumentException.class, () -> u.setEmail("user@@dominio.com"));
        assertThrows(IllegalArgumentException.class, () -> u.setEmail("@dominio.com"));

        reporter.publishEntry("Validações", "Nome/email validados conforme regras");
    }

    @Test
    @DisplayName("2) Deve gerar hash da senha usando MessageDigest (injeção do hasher)")
    void hashingSenhaViaInjecao() {
        PasswordHasher hasher = new MessageDigestPasswordHasher("SHA-256");
        Usuario u = new Usuario(hasher, MutableClock.nowUTC());
        u.setSenha("segredo");
        String h1 = u.getSenhaHash();
        assertNotNull(h1);
        u.setSenha("segredo");
        String h2 = u.getSenhaHash();
        assertEquals(h1, h2, "Hash deve ser determinístico para mesma entrada e algoritmo");
        reporter.publishEntry("Hash", h1.substring(0, Math.min(12, h1.length())) + "...");
    }

    @RepeatedTest(value = 3, name = "3) Tentativa incorreta permitida (repetição {currentRepetition}/{totalRepetitions})")
    void repeticaoTentativasSemBloqueio(RepetitionInfo repInfo) throws Exception {
        MutableClock clock = MutableClock.nowUTC();
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha("correta");

        boolean ok = u.autenticar("errada");
        reporter.publishEntry("Tentativa", "repeticao=" + repInfo.getCurrentRepetition() + ", sucesso=" + ok);
        assertFalse(ok);
    }

    @Test
    @DisplayName("4) Na 4ª falha em 30s deve lançar ExceededAttemptsException e bloquear por 60s")
    void bloqueioNaQuartaFalha() throws Exception {
        MutableClock clock = MutableClock.nowUTC();
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha("correta");

        assertFalse(u.autenticar("x1"));
        assertFalse(u.autenticar("x2"));
        assertFalse(u.autenticar("x3"));

        ExceededAttemptsException ex = assertThrows(ExceededAttemptsException.class, () -> u.autenticar("x4"));
        reporter.publishEntry("Exceção", ex.getMessage());

        clock.advanceSeconds(20);
        assertThrows(ExceededAttemptsException.class, () -> u.autenticar("correta"));

        clock.advanceSeconds(50);
        assertTrue(u.autenticar("correta"));
    }

    @Test
    @DisplayName("5) Após sucesso, contador de falhas deve ser limpo")
    void sucessoLimpaFalhas() throws Exception {
        MutableClock clock = MutableClock.nowUTC();
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha("segredo");

        assertFalse(u.autenticar("a"));
        assertFalse(u.autenticar("b"));

        assertTrue(u.autenticar("segredo"));

        assertFalse(u.autenticar("x1"));
        assertFalse(u.autenticar("x2"));
        assertFalse(u.autenticar("x3"));
    }

    @Test
    @DisplayName("6) Falhas antigas fora da janela de 30s não contam para bloqueio")
    void janelaDeslizante() throws Exception {
        MutableClock clock = MutableClock.nowUTC();
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha("ok");

        assertFalse(u.autenticar("1"));
        clock.advanceSeconds(31);
        assertFalse(u.autenticar("2"));
        clock.advanceSeconds(31);
        assertFalse(u.autenticar("3"));

        assertFalse(u.autenticar("4"));
    }
}
