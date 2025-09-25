package com.teixeira.tdd.password;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    private final PasswordValidator validator = new PasswordValidator();

    @Test
    @DisplayName("Senha válida atende todos os critérios")
    void validPassword() {
        // 8+ chars, A-Z, a-z, 0-9, special, no whitespace
        var result = validator.validate("Abcdef1@");
        assertTrue(result.isValid(), () -> "Erros: " + result.getErrors());
    }

    @Test
    @DisplayName("Nula ou vazia é inválida (NULL_OR_EMPTY)")
    void nullOrEmpty() {
        assertFalse(validator.validate("").isValid());
        assertFalse(validator.validate(null).isValid());
        assertTrue(validator.validate("").getErrors().contains(ValidationError.NULL_OR_EMPTY));
        assertTrue(validator.validate(null).getErrors().contains(ValidationError.NULL_OR_EMPTY));
    }

    @Test
    @DisplayName("Menor que 8 caracteres é inválida (TOO_SHORT)")
    void tooShort() {
        var r = validator.validate("Ab1@x"); // 5 chars
        assertFalse(r.isValid());
        assertTrue(r.getErrors().contains(ValidationError.TOO_SHORT));
    }

    @Test
    @DisplayName("Sem maiúscula é inválida (NO_UPPERCASE)")
    void missingUppercase() {
        var r = validator.validate("abcdef1@");
        assertFalse(r.isValid());
        assertTrue(r.getErrors().contains(ValidationError.NO_UPPERCASE));
    }

    @Test
    @DisplayName("Sem minúscula é inválida (NO_LOWERCASE)")
    void missingLowercase() {
        var r = validator.validate("ABCDEFG1@");
        assertFalse(r.isValid());
        assertTrue(r.getErrors().contains(ValidationError.NO_LOWERCASE));
    }

    @Test
    @DisplayName("Sem dígito é inválida (NO_DIGIT)")
    void missingDigit() {
        var r = validator.validate("Abcdefg@");
        assertFalse(r.isValid());
        assertTrue(r.getErrors().contains(ValidationError.NO_DIGIT));
    }

    @Test
    @DisplayName("Sem caractere especial é inválida (NO_SPECIAL)")
    void missingSpecial() {
        var r = validator.validate("Abcdefg1");
        assertFalse(r.isValid());
        assertTrue(r.getErrors().contains(ValidationError.NO_SPECIAL));
    }

    @ParameterizedTest(name = "Espaço em branco "{0}" torna a senha inválida (HAS_WHITESPACE)")
    @ValueSource(strings = {"Abc def1@", "Abc\tdef1@", "Abc\ndef1@", " Abcdef1@", "Abcdef1@ "})
    void whitespaceNotAllowed(String password) {
        // Nota: na annotation acima, \t e \n serão passados como texto com barra invertida.
        // Para garantir whitespaces reais, substituímos aqui.
        password = password.replace("\\t", "\t").replace("\\n", "\n");
        var r = validator.validate(password);
        assertFalse(r.isValid());
        assertTrue(r.getErrors().contains(ValidationError.HAS_WHITESPACE));
    }

    @Test
    @DisplayName("Várias violações são agregadas (ex.: curta, sem maiúscula e sem especial)")
    void aggregatesMultipleViolations() {
        var r = validator.validate("abc1"); // curta, sem maiúscula, sem especial
        assertFalse(r.isValid());
        assertTrue(r.getErrors().contains(ValidationError.TOO_SHORT));
        assertTrue(r.getErrors().contains(ValidationError.NO_UPPERCASE));
        assertTrue(r.getErrors().contains(ValidationError.NO_SPECIAL));
    }

    @Test
    @DisplayName("Política customizada: 12+ chars")
    void customPolicyMinLength() {
        var longPolicy = new PasswordPolicy(12, true, true, true, true, true);
        var customValidator = new PasswordValidator(longPolicy);
        assertFalse(customValidator.isValid("Abcdef1@"));        // apenas 8 chars
        assertTrue(customValidator.isValid("AbcdefGhij1@"));     // 12+ e cumpre demais critérios
    }
}
