# Conversor de Moedas — TDD (Java, JUnit 5 + Mockito)

Este projeto implementa um **Conversor de Moedas** usando **TDD**. A dependência externa de taxa de câmbio é isolada por **dublês** (mocks via Mockito).

## Requisitos
- Java 17+
- Maven 3.8+

## Rodando os testes
```bash
mvn -q test
```

Você deverá ver todos os testes passando ✅.

## Estrutura
```
currency-converter/
├─ pom.xml
├─ src/
│  ├─ main/java/com/teixeira/tdd/currency/
│  │  ├─ CurrencyConverter.java
│  │  ├─ ExchangeRateProvider.java
│  │  ├─ ExchangeRateUnavailableException.java
│  │  └─ InMemoryExchangeRateProvider.java   (exemplo opcional)
│  └─ test/java/com/teixeira/tdd/currency/
│     └─ CurrencyConverterTest.java
```

## Observações
- Arredondamento: `HALF_UP` para 2 casas decimais.
- Códigos de moeda normalizados para **MAIÚSCULAS** e validados por regex `[A-Za-z]{3}`.
- Lança `IllegalArgumentException` para montante negativo e códigos inválidos.
- Exemplo adicional `InMemoryExchangeRateProvider` incluso para exploração manual.

## Conversão amigável (nomes e países)
Além do conversor básico por **código ISO**, o projeto inclui um serviço que aceita **nomes de moedas** e **nomes de países**, resolvendo-os para códigos ISO 4217 mais comuns (USD, EUR, BRL, GBP, JPY, CNY, AUD, CAD, CHF, ARS, CLP, MXN, COP, INR, RUB, ZAR).

### Classes novas
- `CurrencyResolver` — resolve texto livre (ex.: "Dólar Americano", "Brasil", "yen") para código ISO.
- `CurrencyConversionService` — usa `CurrencyResolver` + `ExchangeRateProvider` + `CurrencyConverter` para converter de/para nomes amigáveis.
- `AmbiguousCurrencyException` e `CurrencyNotFoundException` — tratam casos de ambiguidade/ausência.

### Exemplos
```java
var resolver = new CurrencyResolver();
var service = new CurrencyConversionService(provider, resolver);

service.convert("Dólar Americano", "Real Brasileiro", new BigDecimal("10")); // USD->BRL
service.convert("Estados Unidos", "Brasil", new BigDecimal("10"));           // país->país
service.convert("usd", "euro", new BigDecimal("10"));                        // misto
```
