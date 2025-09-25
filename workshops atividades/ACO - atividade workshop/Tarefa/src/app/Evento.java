package app;
import java.util.ArrayList;
import java.util.List;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kensl
 */

public class Evento {
    private String nome;
    private List<Evento> vizinhos;

    public Evento(String nome) {
        this.nome = nome;
        this.vizinhos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Evento> getVizinhos() {
        return vizinhos;
    }

    public void adicionarVizinho(Evento vizinho) {
        vizinhos.add(vizinho);
    }

    @Override
    public String toString() {
        return nome;
    }
}
