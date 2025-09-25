# Test Doubles Demo (EasyMock & Mockito)

Projeto Maven com exemplos práticos de **Dummy**, **Fake** e **Test Spy**, além de testes usando **EasyMock** e **Mockito**.

## Estrutura
- `src/main/java`: classes do SUT e dublês manuais (Fake, Spy, Dummy).
- `src/test/java`: testes com EasyMock e Mockito.
- `docs/RESUMO.md`: respostas organizadas às questões 1–4 (resumo).
- `docs/easymock-outputs.txt`: saídas exemplificativas pedidas para o item 2.1.

## Como executar
```bash
mvn -q -e -DskipTests=false test
```

## Observação
Os exemplos foram escritos para serem **autoexplicativos** e pequenos. Ajuste versões das libs no `pom.xml` se necessário.
