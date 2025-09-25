package com.teixeira.auth;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.regex.Pattern;

public class Usuario {
    private String nome;
    private String email;
    private String senhaHash;

    private final PasswordHasher hasher;
    private final Clock clock;

    private final Deque<Instant> falhasUltimos30s = new ArrayDeque<>();
    private Instant bloqueadoAte;

    public static final int JANELA_SEGUNDOS = 30;
    public static final int LIMITE_FALHAS = 3;
    public static final int BLOQUEIO_SEGUNDOS = 60;

    private static final Pattern NOME_REGEX = Pattern.compile("^[A-Za-z]+(?: [A-Za-z]+)*$");
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9._]+@[A-Za-z0-9]+(?:\.[A-Za-z0-9]+)+$");

    public Usuario(PasswordHasher hasher, Clock clock) {
        this.hasher = Objects.requireNonNull(hasher, "hasher");
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenhaHash() { return senhaHash; }
    public Clock getClock() { return clock; }

    public void setNome(String nome) {
        validarNaoVazio(nome, "nome");
        if (!validarNome(nome)) {
            throw new IllegalArgumentException("Nome inválido. Use somente letras e espaços simples entre palavras.");
        }
        this.nome = nome;
    }

    public void setEmail(String email) {
        validarNaoVazio(email, "email");
        if (!validarEmail(email)) {
            throw new IllegalArgumentException("Email inválido. Formato esperado usuario@dominio.tld");
        }
        this.email = email;
    }

    public void setSenha(String senha) {
        validarNaoVazio(senha, "senha");
        this.senhaHash = hasher.hash(senha);
    }

    public boolean autenticar(String senhaDigitada) throws ExceededAttemptsException {
        Instant agora = Instant.now(clock);

        if (bloqueadoAte != null && agora.isBefore(bloqueadoAte)) {
            throw new ExceededAttemptsException("Usuário bloqueado até " + bloqueadoAte + " (" + ZoneId.systemDefault() + ")");
        }

        if (bloqueadoAte != null && !agora.isBefore(bloqueadoAte)) {
            bloqueadoAte = null;
            falhasUltimos30s.clear();
        }

        validarNaoVazio(senhaDigitada, "senhaDigitada");

        String tentativaHash = hasher.hash(senhaDigitada);
        boolean sucesso = tentativaHash.equals(this.senhaHash);

        if (sucesso) {
            falhasUltimos30s.clear();
            return true;
        }

        registrarFalha(agora);
        if (falhasUltimos30s.size() >= (LIMITE_FALHAS + 1)) {
            bloqueadoAte = agora.plusSeconds(BLOQUEIO_SEGUNDOS);
            throw new ExceededAttemptsException("Limite de tentativas excedido. Aguarde 60 segundos para tentar novamente.");
        }
        return false;
    }

    private void registrarFalha(Instant agora) {
        Instant limite = agora.minusSeconds(JANELA_SEGUNDOS);
        while (!falhasUltimos30s.isEmpty() && falhasUltimos30s.peekFirst().isBefore(limite)) {
            falhasUltimos30s.removeFirst();
        }
        falhasUltimos30s.addLast(agora);
    }

    public static boolean validarNome(String nome) {
        return nome != null && !nome.isBlank() && NOME_REGEX.matcher(nome).matches();
    }

    public static boolean validarEmail(String email) {
        return email != null && !email.isBlank() && EMAIL_REGEX.matcher(email).matches();
    }

    private static void validarNaoVazio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Campo '" + campo + "' não pode ser nulo ou vazio");
        }
    }
}
