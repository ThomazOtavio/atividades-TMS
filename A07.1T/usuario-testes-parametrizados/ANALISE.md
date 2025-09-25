# Comparação: ValueSource vs EnumSource vs CsvSource vs CsvFileSource

**ValueSource**

- **Vantagens**: configuração simples e direta; ótimo para listas pequenas de valores (strings, números, booleanos). 
- **Desvantagens**: não suporta múltiplos parâmetros por linha; dados ficam "colados" no código, pouco escalável.

**EnumSource**

- **Vantagens**: evita typos de strings para constantes (ex.: algoritmos); fácil adicionar/remover opções; ótima integração com enums do domínio.
- **Desvantagens**: exige criação/manutenção do enum; menos flexível para associar múltiplos parâmetros.

**CsvSource**

- **Vantagens**: suporta **múltiplos parâmetros** por caso; sintaxe curta; ideal para variações pequenas/médias de dados.
- **Desvantagens**: dados continuam no código da classe de teste; linhas muito longas podem reduzir legibilidade.

**CsvFileSource**

- **Vantagens**: separa dados do código; facilita manutenção, revisão e crescimento do conjunto de casos; pode ser reutilizado por várias classes de teste.
- **Desvantagens**: exige arquivos adicionais e organização de recursos; pode dificultar leitura rápida do caso no próprio teste.
