# Mine Fields — TDD (Java, JUnit 5)

Gera um campo de dicas a partir de um campo de minas (`*` = mina, `.` = vazio).
Cada casa vazia mostra o **número de minas adjacentes** (8 vizinhos).

## Exemplo
Entrada:
```
*...
..*.
....
```
Saída:
```
*211
12*1
0111
```

## Rodar testes
```bash
mvn -q test
```
