package org.example.doubles;

// Dummy: implementa mas não é utilizado no fluxo testado
public class DummyLogger implements Logger {
    @Override public void info(String msg) { /* no-op */ }
    @Override public void warn(String msg) { /* no-op */ }
}
