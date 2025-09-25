# Usuário — Testes Parametrizados (2) com Hamcrest

Projeto Maven (Java 17) pronto para o IntelliJ com **versões alternativas dos testes** usando Hamcrest,
conforme os slides (págs. 66–98) e os tutoriais do **Hamcrest.org** e **Vogella**.

## Rodar
- IntelliJ: ícone de *Run* nos testes
- Terminal: `mvn -q -DskipTests=false test`

## Notas
- Usamos `org.hamcrest:hamcrest:3.0` (jar único com API + matchers). Se preferir seguir o snippet antigo
  dos slides com `hamcrest-library`, troque a dependência no `pom.xml`.
