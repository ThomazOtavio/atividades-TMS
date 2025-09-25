# Jogo da Velha — TDD (Java, JUnit 5)

Implementação do **Tic-Tac-Toe** com **TDD**:
- Tabuleiro 3x3, jogador inicial **X**
- Alternância de turnos **X ↔ O**
- Verificação de vitória (linhas, colunas e diagonais) após cada jogada
- Detecção de **empate** quando o tabuleiro enche sem vencedor
- Rejeita jogadas inválidas (fora do tabuleiro, casa ocupada, ou após término do jogo)

## Rodando os testes
```bash
mvn -q test
```

## Estrutura
```
tic-tac-toe/
├─ pom.xml
├─ src/
│  ├─ main/java/com/teixeira/tdd/tictactoe/
│  │  ├─ TicTacToe.java
│  │  ├─ GameState.java
│  │  ├─ Player.java
│  │  └─ InvalidMoveException.java
│  └─ test/java/com/teixeira/tdd/tictactoe/
│     └─ TicTacToeTest.java
```
