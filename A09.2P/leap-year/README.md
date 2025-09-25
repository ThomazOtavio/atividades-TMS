# Leap Year — TDD (Java, JUnit 5)

Retorna **true** se o ano for bissexto e **false** caso contrário.

**Regra**: Divisível por 4, exceto se for divisível por 100; nesse caso, só é bissexto se também for divisível por 400.

## Exemplos
- 1996 → bissexto (true)
- 2001 → comum (false)
- 1900 → comum (false)
- 2000 → bissexto (true)

## Rodar testes
```bash
mvn -q test
```
