package com.teixeira.auth;

import com.teixeira.auth.util.MutableClock;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Usuario - Testes Parametrizados")
class UsuarioParameterizedTest {

    enum HashAlg {
        SHA_256("SHA-256"),
        MD5("MD5"),
        SHA_1("SHA-1");
        private final String jca;
        HashAlg(String jca) { this.jca = jca; }
        public String jca() { return jca; }
    }

    private MutableClock clock;
    private TestReporter reporter;

    @BeforeEach
    void setup(TestInfo info, TestReporter reporter) {
        this.clock = MutableClock.nowUTC();
        this.reporter = reporter;
        reporter.publishEntry("Iniciando", info.getDisplayName());
    }

    // 1) Diferentes valores para autenticação via @ValueSource
    @ParameterizedTest(name = "Falha com senha incorreta: "{0}"")
    @ValueSource(strings = {"x", "123", "senhaErrada", "Segredo", "segredo!"})
    void falhasComValueSource(String tentativa) throws Exception {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha("segredo");
        boolean ok = u.autenticar(tentativa);
        reporter.publishEntry("Tentativa", tentativa + " => " + ok);
        assertFalse(ok);
    }

    // 2) Sobrecarga autenticar(setSenha/autenticar com algoritmo) + 3) @ValueSource de algoritmos
    @ParameterizedTest(name = "Autenticação com algoritmo {0} deve ser bem-sucedida")
    @ValueSource(strings = {"SHA-256", "MD5", "SHA-1"})
    void autenticarComAlgoritmo_ValueSource(String algoritmo) throws Exception {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha("abc123", algoritmo); // define o hash com o algoritmo informado
        assertTrue(u.autenticar("abc123", algoritmo));
        assertFalse(u.autenticar("zzz", algoritmo));
    }

    // 2) Exceção para algoritmo desconhecido
    @ParameterizedTest(name = "Algoritmo desconhecido deve lançar exceção: {0}")
    @ValueSource(strings = {"SHA-512/256", "XYZ", "MD6"})
    void algoritmoDesconhecidoDisparaExcecao(String algoritmo) {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        assertThrows(IllegalArgumentException.class, () -> u.setSenha("abc", algoritmo));
        assertThrows(IllegalArgumentException.class, () -> u.autenticar("abc", algoritmo));
    }

    // 3) Versão com @EnumSource
    @ParameterizedTest(name = "EnumSource - Autenticação com {0}")
    @EnumSource(HashAlg.class)
    void autenticarComAlgoritmo_EnumSource(HashAlg alg) throws Exception {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha("pw", alg.jca());
        assertTrue(u.autenticar("pw", alg.jca()));
        assertFalse(u.autenticar("wrong", alg.jca()));
    }

    // 4) @CsvSource (algoritmo, senhaCorreta, tentativa, esperado)

    @ParameterizedTest(name = "CsvSource - {0} com senha "{1}" tentativa "{2}" => {3}")
    @CsvSource({
            "SHA-256, segredo, segredo, true",
            "MD5, segredo, x, false",
            "SHA-1, 123, 123, true",
            "SHA-256, 123, 321, false"
    })
    void autenticar_CsvSource(String algoritmo, String senhaCorreta, String tentativa, boolean esperado) throws Exception {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha(senhaCorreta, algoritmo);
        boolean ok = u.autenticar(tentativa, algoritmo);
        assertEquals(esperado, ok);
    }

    // 4) @CsvFileSource (dados em src/test/resources/auth-cases.csv)

    @ParameterizedTest(name = "CsvFileSource - {0} com senha "{1}" tentativa "{2}" => {3}")
    @CsvFileSource(resources = "/auth-cases.csv", numLinesToSkip = 1)
    void autenticar_CsvFileSource(String algoritmo, String senhaCorreta, String tentativa, boolean esperado) throws Exception {
        Usuario u = new Usuario(new MessageDigestPasswordHasher("SHA-256"), clock);
        u.setSenha(senhaCorreta, algoritmo);
        boolean ok = u.autenticar(tentativa, algoritmo);
        assertEquals(esperado, ok);
    }
}
