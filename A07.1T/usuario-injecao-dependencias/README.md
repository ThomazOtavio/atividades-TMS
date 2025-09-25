# Usuário — Injeção de Dependências (JUnit 5)

Projeto Maven pronto para importar no IntelliJ.

## Rodando os testes
1. `mvn -q -e -DskipTests=false test` ou clique no ícone de teste no IntelliJ.
2. Requer Java 17.

## Estrutura
- `Usuario` com validações (nome, email, senha) e regras de autenticação
- Injeção de `PasswordHasher` e `Clock`
- `ExceededAttemptsException`
- Testes com `TestInfo`, `TestReporter` e `RepetitionInfo`
