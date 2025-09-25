# Análise: versões com Hamcrest (págs. 66–98 + tutoriais)
- `assertThat` + `Matchers` (Hamcrest) deixam asserções mais legíveis (ex.: `is(true)`, `notNullValue()`, `containsString()`).
- Substituímos cadeias de `assertTrue(a || b || c)` por `anyOf(…)` e comparações por `is(equalTo(…))`.
- Para exceções, mantivemos `assertThrows` do JUnit e validamos a mensagem com `containsString()`.
- Parametrizados (ValueSource/EnumSource/CsvSource/CsvFileSource) continuam iguais, apenas trocaram os asserts para Hamcrest.
- Exemplos: veja `UsuarioHamcrestTest` (cada método tem um @DisplayName que explica o uso).

## Quando usar o quê
- **is / equalTo / not**: para valores simples / booleans.
- **anyOf / allOf**: combinar condições (equivalente a `||` e `&&`). 
- **containsString / startsWith / endsWith**: textos e mensagens.
- **nullValue / notNullValue / instanceOf**: presença e tipo de objetos.
- **closeTo / greaterThan / lessThan**: números.

## Observação de dependência
- Projeto usa `org.hamcrest:hamcrest:3.0` (jar único). Se preferir o snippet antigo com `hamcrest-library`, basta ajustar o pom.
