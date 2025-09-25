# Conversor de Unidades — TDD (Java, JUnit 5)

Conversor com **TDD** para Comprimento, Peso e Temperatura.

## Suporte
- **Comprimento**: metros (`m`), quilômetros (`km`), centímetros (`cm`), milhas (`mi`), polegadas (`in`)
- **Peso**: gramas (`g`), quilogramas (`kg`), libras (`lb`), onças (`oz`)
- **Temperatura**: Celsius (`C`), Fahrenheit (`F`), Kelvin (`K`)

> Implementação orientada a testes (testes primeiro).

## Rodando os testes
```bash
mvn -q test
```

## Estrutura
```
unit-converter/
├─ pom.xml
├─ src/
│  ├─ main/java/com/teixeira/tdd/unit/
│  │  ├─ UnitConverter.java
│  │  ├─ InvalidUnitException.java
│  │  └─ IncompatibleUnitException.java
│  └─ test/java/com/teixeira/tdd/unit/
│     └─ UnitConverterTest.java
```
