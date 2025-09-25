/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author kensl
 */

public class Formiga {
    private Map<String, Evento> grafo;
    private Map<String, Map<String, Double>> feromonios;
    private Set<String> visitados = new HashSet<>();
    private double alfa, beta;

    public Formiga(Map<String, Evento> grafo, Map<String, Map<String, Double>> feromonios, double alfa, double beta) {
        this.grafo = grafo;
        this.feromonios = feromonios;
        this.alfa = alfa;
        this.beta = beta;
    }

    public List<Evento> construirCaminho(String inicio, String destino) {
        List<Evento> caminho = new ArrayList<>();
        Evento atual = grafo.get(inicio);

        if (atual == null) {
            System.err.println("Erro: Evento inicial '" + inicio + "' não encontrado no grafo.");
            return null;
        }

        caminho.add(atual);
        visitados.clear();
        visitados.add(atual.getNome());

        while (!atual.getNome().equals(destino)) {
            List<Evento> vizinhos = atual.getVizinhos();
            Map<Evento, Double> probabilidades = new HashMap<>();

            double soma = 0.0;
            for (Evento vizinho : vizinhos) {
                if (!visitados.contains(vizinho.getNome())) {
                    double tau = feromonios.get(atual.getNome()).get(vizinho.getNome());
                    double eta = 1.0 / vizinho.getVizinhos().size(); // Menos ramificações = melhor
                    double valor = Math.pow(tau, alfa) * Math.pow(eta, beta);
                    probabilidades.put(vizinho, valor);
                    soma += valor;
                }
            }

            if (probabilidades.isEmpty()) break;

            Evento proximo = roleta(probabilidades, soma);
            caminho.add(proximo);
            visitados.add(proximo.getNome());
            atual = proximo;
        }

        return atual.getNome().equals(destino) ? caminho : null;
    }

    private Evento roleta(Map<Evento, Double> probabilidades, double soma) {
        double r = Math.random() * soma;
        double acumulado = 0.0;
        for (Map.Entry<Evento, Double> entry : probabilidades.entrySet()) {
            acumulado += entry.getValue();
            if (acumulado >= r) {
                return entry.getKey();
            }
        }
        // fallback (não deveria acontecer)
        return probabilidades.keySet().iterator().next();
    }
}