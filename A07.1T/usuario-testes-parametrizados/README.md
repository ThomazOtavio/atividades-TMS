# Usuário — Testes Parametrizados (JUnit 5)

Projeto Maven pronto para importar no IntelliJ, baseado na atividade de "Testes Parametrizados".

## Como rodar
- IntelliJ: botão de rodar nos testes.
- Terminal: `mvn -q -DskipTests=false test`

## O que tem aqui
- `Usuario` com injeção de `PasswordHasher` e `Clock`.
- Sobrecargas: `setSenha(senha, algoritmo)` e `autenticar(senha, algoritmo)`.
- Testes com `@ValueSource`, `@EnumSource`, `@CsvSource` e `@CsvFileSource`.
- `MutableClock` para simular passagem de tempo.
