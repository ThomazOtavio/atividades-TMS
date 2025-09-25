/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.util.ArrayList;
import java.util.Collections;
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


public class ACO {
    private Map<String, Evento> grafo;
    private Map<String, Map<String, Double>> feromonios = new HashMap<>();
    private double alfa = 1.5;  // influência do feromônio -> Valor 1 e 2
    private double beta = 3.5;  // influência da heurística -> Valor >= 2; 
    private double evaporacao = 0.5; // Valores típicos 0.1 - 0.5
    private int numFormigas = 40; // Tamanho da colonia de formigas
    private int iteracoes = 100;

    public ACO(Map<String, Evento> grafo) {
        this.grafo = grafo;
        inicializarFeromonios();
    }
    
    private void inicializarFeromonios() {
        for (Evento origem : grafo.values()) {
            feromonios.put(origem.getNome(), new HashMap<>());
            for (Evento destino : origem.getVizinhos()) {
                feromonios.get(origem.getNome()).put(destino.getNome(), 1.0);
            }
        }
    }

    public List<Evento> encontrarMelhorCaminho(String inicio, String destino) {
        
        if (!grafo.containsKey(inicio) || !grafo.containsKey(destino)) {
            System.err.println("Erro: Ponto inicial ou final não encontrado no grafo.");
            return Collections.emptyList();
        }

        List<Evento> melhorCaminho = null;
        double melhorCusto = Double.MAX_VALUE;

        for (int i = 0; i < iteracoes; i++) {
            List<List<Evento>> caminhos = new ArrayList<>();

            for (int j = 0; j < numFormigas; j++) {
                Formiga formiga = new Formiga(grafo, feromonios, alfa, beta);
                List<Evento> caminho = formiga.construirCaminho(inicio, destino);
                if (caminho != null) {
                    caminhos.add(caminho);
                }
            }

            // Atualizar feromônios
            evaporarFeromonios();
            for (List<Evento> caminho : caminhos) {
                double custo = caminho.size();
                double deposito = 1.0 / custo;
                for (int k = 0; k < caminho.size() - 1; k++) {
                    String de = caminho.get(k).getNome();
                    String para = caminho.get(k + 1).getNome();
                    feromonios.get(de).put(para, feromonios.get(de).get(para) + deposito);
                }

                if (custo < melhorCusto) {
                    melhorCusto = custo;
                    melhorCaminho = caminho;
                }
            }
        }

        return melhorCaminho;
    }

    private void evaporarFeromonios() {
        for (String origem : feromonios.keySet()) {
            for (String destino : feromonios.get(origem).keySet()) {
                double valorAtual = feromonios.get(origem).get(destino);
                feromonios.get(origem).put(destino, valorAtual * (1 - evaporacao));
            }
        }
    }
}
