# Validador de Senhas — TDD (Java, JUnit 5)

Implementação em **TDD** de um validador de senhas que considera TODOS os critérios informados no enunciado.

## Critérios de Validação (padrão)
- Pelo menos **8 caracteres**
- **≥1 letra maiúscula**
- **≥1 letra minúscula**
- **≥1 dígito**
- **≥1 caractere especial** (qualquer coisa que **não** seja letra, número ou espaço)
- **Sem espaços em branco** (não pode conter espaços, tabs, quebras de linha, etc.)

> A senha é válida **apenas se todos os critérios forem atendidos**.

## Rodando os testes
```bash
mvn -q test
```

## Estrutura
```
password-validator/
├─ pom.xml
├─ src/
│  ├─ main/java/com/teixeira/tdd/password/
│  │  ├─ PasswordPolicy.java
│  │  ├─ PasswordValidator.java
│  │  ├─ ValidationError.java
│  │  └─ ValidationResult.java
│  └─ test/java/com/teixeira/tdd/password/
│     └─ PasswordValidatorTest.java
```
