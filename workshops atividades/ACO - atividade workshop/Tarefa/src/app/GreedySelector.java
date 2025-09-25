/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author kensl
 */
public class GreedySelector {
     public static List<List<Integer>> selecionar(List<List<Integer>> caminhos, int totalEventos) {
        List<List<Integer>> resultado = new ArrayList<>();
        Set<Integer> cobertos = new HashSet<>();

        while (cobertos.size() < totalEventos) {
            List<Integer> melhor = null;
            int maxCobertura = -1;

            for (List<Integer> caminho : caminhos) {
                Set<Integer> temp = new HashSet<>(caminho);
                temp.removeAll(cobertos);
                if (temp.size() > maxCobertura) {
                    melhor = caminho;
                    maxCobertura = temp.size();
                }
            }

            if (melhor == null) break;

            resultado.add(melhor);
            cobertos.addAll(melhor);
            caminhos.remove(melhor);
        }
        return resultado;
    }
}
