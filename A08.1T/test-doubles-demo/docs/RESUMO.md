# Resumo — Dublês de Teste, EasyMock e Mockito

## 1) Dummy, Fake e Test Spy
- **Dummy**: objeto apenas para preencher parâmetro; não é usado pelo SUT.
- **Fake**: implementação funcional simplificada (ex.: repositório em memória).
- **Test Spy**: registra o uso (quantidade/argumentos) para verificação posterior.

**Diferenças chave**: Dummy não tem comportamento; Fake tem comportamento “real” simplificado; Spy registra interações (histórico), enquanto um mock costuma checar expectativas de interação.

**Exemplos no projeto**:
- Dummy: `DummyLogger` passado a métodos que não usam o logger.
- Fake: `InMemoryAccountManager` (contas em memória) usado por `AccountService`.
- Spy: `EmailServiceSpy` registra os emails enviados para asserts.

## 2) EasyMock — Getting Started + amostras
Fluxo típico: **record → replay → act → verify**. No projeto:
- `EasyMockDocumentServiceTest` demonstra:
  1. **Caminho feliz**: expectativa atendida e `verify()` OK.
  2. **Chamada inesperada**: método não gravado em expectativa gera falha.
  3. **Expectativa não atendida**: `verify()` acusa chamada pendente.

Veja também `docs/easymock-outputs.txt` com 3 saídas ilustrativas.

## 3) Mockito — exemplos notáveis
- Verificação de comportamento simples com `mock(...)` e `verify(...)`.
- Stubbing com matchers (`anyInt()`, `when(...).thenReturn(...)`).
- Spies em objetos reais com `spy(...)` e família `doReturn/...` para evitar efeitos colaterais.

## 4) Comparativo (alto nível)
- **EasyMock**: estilo **record–replay–verify** e mensagens detalhadas quando expectativas falham. Ótimo quando queremos contratos explícitos de colaboração.
- **Mockito**: API fluente e ergonômica (A/A/A), `verify` flexível (contagem, in-order), `ArgumentCaptor`, `spy` nativo.

**Mapeamento para dublês**:  
- Dummy/Stub/Mock: ambos frameworks atendem bem.  
- Spy: mais natural em Mockito (API dedicada).  
- Fake: normalmente manual (como `InMemoryAccountManager`).

