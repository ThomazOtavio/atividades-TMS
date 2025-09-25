package com.teixeira.auth;

import com.teixeira.auth.util.MutableClock;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Usuario - Versões alternativas com Hamcrest")
class UsuarioHamcrestTest {

    enum HashAlg { SHA_256("SHA-256"), MD5("MD5"), SHA_1("SHA-1"); private final String jca; HashAlg(String jca){this.jca=jca;} public String jca(){return jca;} }

    private MutableClock clock;
    private TestReporter reporter;

    @BeforeEach
    void setup(TestInfo info, TestReporter reporter) {
        this.clock = MutableClock.nowUTC();
        this.reporter = reporter;
        reporter.publishEntry("Iniciando", info.getDisplayName());
    }

    @Test
    @DisplayName("Validações de nome/email ficam mais legíveis com matchers")
    void validacoesComHamcrest() {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);

        u.setNome("Maria Silva");
        u.setEmail("maria.silva_01@dominio.com");

        assertThat(u.getNome(), allOf(not(is(emptyOrNullString())), equalTo("Maria Silva")));
        assertThat(u.getEmail(), allOf(containsString("@"), endsWith(".com")));

        // mensagens de erro verificadas com matchers
        IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> u.setNome("Ana-123"));
        assertThat(e1.getMessage(), anyOf(containsString("Nome inválido"), containsString("somente letras")));

        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> u.setEmail("user@dominio"));
        assertThat(e2.getMessage(), containsString("Email inválido"));
    }

    @Test
    @DisplayName("Hashing determinístico com is(equalTo())")
    void hashingDeterministico() {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha("segredo");
        String h1 = u.getSenhaHash();
        u.setSenha("segredo");
        String h2 = u.getSenhaHash();
        assertThat(h1, is(equalTo(h2)));
        assertThat(h1, not(is(emptyOrNullString())));
    }

    @RepeatedTest(3)
    @DisplayName("Tentativa incorreta -> is(false)")
    void repeticaoTentativaIncorreta() throws Exception {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha("correta");
        boolean ok = u.autenticar("errada");
        assertThat(ok, is(false));
    }

    @Test
    @DisplayName("Na 4ª falha em 30s -> ExceededAttemptsException; bloqueio por 60s")
    void bloqueioComHamcrest() throws Exception {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha("pw");

        assertThat(u.autenticar("x1"), is(false));
        assertThat(u.autenticar("x2"), is(false));
        assertThat(u.autenticar("x3"), is(false));

        ExceededAttemptsException ex = assertThrows(ExceededAttemptsException.class, () -> u.autenticar("x4"));
        assertThat(ex.getMessage(), anyOf(containsString("Limite de tentativas"), containsString("bloqueado")));

        clock.advanceSeconds(20);
        assertThrows(ExceededAttemptsException.class, () -> u.autenticar("pw"));

        clock.advanceSeconds(50);
        assertThat(u.autenticar("pw"), is(true));
    }

    // ---- Parametrizados reescritos com Hamcrest ----

    @ParameterizedTest(name = "Falha com senha incorreta: "{0}" (ValueSource)")
    @ValueSource(strings = {"x", "123", "Senha!", "segredo!"})
    void falhasComValueSource(String tentativa) throws Exception {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha("segredo");
        boolean ok = u.autenticar(tentativa);
        assertThat(ok, is(false));
    }

    @ParameterizedTest(name = "Autenticar com algoritmo {0} (ValueSource)")
    @ValueSource(strings = {"SHA-256", "MD5", "SHA-1"})
    void autenticarAlgoritmo_ValueSource(String algoritmo) throws Exception {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha("abc123", algoritmo);
        assertThat(u.autenticar("abc123", algoritmo), is(true));
        assertThat(u.autenticar("zzz", algoritmo), is(false));
    }

    @ParameterizedTest(name = "Algoritmo desconhecido deve lançar exceção: {0}")
    @ValueSource(strings = {"SHA-512/256", "XYZ", "MD6"})
    void algoritmoDesconhecido(String algoritmo) {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        assertThat(assertThrows(IllegalArgumentException.class, () -> u.setSenha("abc", algoritmo)).getMessage(),
                containsString("Unsupported"));
        assertThat(assertThrows(IllegalArgumentException.class, () -> u.autenticar("abc", algoritmo)).getMessage(),
                containsString("Unsupported"));
    }

    @ParameterizedTest(name = "EnumSource - {0}")
    @EnumSource(HashAlg.class)
    void autenticarAlgoritmo_EnumSource(HashAlg alg) throws Exception {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha("pw", alg.jca());
        assertThat(u.autenticar("pw", alg.jca()), is(true));
        assertThat(u.autenticar("wrong", alg.jca()), is(false));
    }

    @ParameterizedTest(name = "CsvSource - {0}, senha "{1}", tentativa "{2}" => {3}")
    @CsvSource({
            "SHA-256, segredo, segredo, true",
            "MD5, segredo, x, false",
            "SHA-1, 123, 123, true",
            "SHA-256, 123, 321, false"
    })
    void autenticar_CsvSource(String algoritmo, String senhaCorreta, String tentativa, boolean esperado) throws Exception {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha(senhaCorreta, algoritmo);
        assertThat(u.autenticar(tentativa, algoritmo), is(esperado));
    }

    @ParameterizedTest(name = "CsvFileSource - {0}, senha "{1}", tentativa "{2}" => {3}")
    @CsvFileSource(resources = "/auth-cases.csv", numLinesToSkip = 1)
    void autenticar_CsvFileSource(String algoritmo, String senhaCorreta, String tentativa, boolean esperado) throws Exception {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha(senhaCorreta, algoritmo);
        assertThat(u.autenticar(tentativa, algoritmo), is(esperado));
    }
}
