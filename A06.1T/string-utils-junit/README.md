# String Utilities (JUnit 5) — IntelliJ / Maven

Implements and tests string operations:
- Reverse (`reverse`): code point–aware (preserves emoji and surrogate pairs).
- Count occurrences:
  - `countOccurrences(String, char)` for simple cases.
  - `countOccurrencesCodePoint(String, int)` for emoji/out-of-BMP.
- Palindrome (`isPalindrome`): ignora **não alfanuméricos** e **maiúsculas/minúsculas**; funciona por code points.
- Case conversion: `toUpper`/`toLower` usando `Locale.ROOT` (evita efeitos de locale).

## Regras / Assunções (para alinhar testes)
- Métodos lançam `IllegalArgumentException` se a string de entrada for **null**.
- `isPalindrome("")` é **verdadeiro** (convencional).
- `isPalindrome` remove caracteres que **não** são `Character.isLetterOrDigit` e compara em **minúsculas**.
- `reverse` e `countOccurrencesCodePoint` iteram por **code points** (Unicode seguro).
- `countOccurrences` (char) trabalha por **unidades UTF-16** (ex.: não “enxerga” um emoji como 1 caractere — use a versão por code point).

## Abrir no IntelliJ
1. **File → New → Project from Existing Sources…** e selecione o `pom.xml`.
2. Garanta JDK 17+.
3. Execute os testes (ícones verdes) ou via **Maven → Lifecycle → test**.

## CLI
```bash
mvn -q -DskipTests=false test
```
