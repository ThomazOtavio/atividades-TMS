# Task Manager (JUnit 5) — IntelliJ / Maven

Funcionalidades:
- **Adicionar**, **remover** (por ID ou por título) e **marcar como concluída** (uma ou todas).
- **Filtrar** por status e por título (contains, case-insensitive).
- **Ordenar** por data de criação (asc/desc).
- **Salvar** e **carregar** tarefas em **arquivo CSV** (com escaping de aspas, vírgula e quebras de linha).

Decisões (para alinhar testes):
- Tarefas têm `UUID` como ID (permite **títulos duplicados**).
- `removeTask(UUID)` remove apenas a tarefa do ID informado.
- `removeAllByTitle(String)` remove todas as tarefas com o mesmo título.
- `markCompleted(UUID)` e `removeTask(UUID)` retornam `false` se o ID não existir.
- Formato CSV com cabeçalho: `id,title,description,status,createdAt` (epoch millis).

## Abrir no IntelliJ
1. **File → New → Project from Existing Sources…** e selecione `pom.xml`.
2. Garanta JDK 17+.
3. Rode os testes pelo ícone verde ou via **Maven → Lifecycle → test**.

## CLI
```bash
mvn -q -DskipTests=false test
```
