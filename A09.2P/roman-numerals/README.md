# Conversão Arábicos ↔ Romanos — TDD (Java, JUnit 5)

Conversor bidirecional de **números arábicos** e **romanos** implementado com **TDD**.
Inclui validação rigorosa (lança exceções para entradas inválidas como `MMXXIIV`, `XLAC`, `IIV`, etc.).

## Suporte
- `toRoman(int)` para 1..3999
- `toArabic(String)` aceita minúsculas/maiúsculas e valida forma canônica
- Regras subtrativas: **IV, IX, XL, XC, CD, CM**
- Exceções específicas: `InvalidRomanNumeralException`, `OutOfRangeException`

## Rodando os testes
```bash
mvn -q test
```

## Estrutura
```
roman-numerals/
├─ pom.xml
├─ src/
│  ├─ main/java/com/teixeira/tdd/roman/
│  │  ├─ RomanNumeralConverter.java
│  │  ├─ InvalidRomanNumeralException.java
│  │  └─ OutOfRangeException.java
│  └─ test/java/com/teixeira/tdd/roman/
│     └─ RomanNumeralConverterTest.java
```
