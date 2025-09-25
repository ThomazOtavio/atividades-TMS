/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *                                   
 * @author kensl
 */

public class GUI extends JFrame {

    private JTextArea resultadoArea;

    public GUI() {
        setTitle("ACO - Caminho Otimizado no App UFU");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Navegação Otimizada com ACO", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultadoArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton executarBtn = new JButton("Executar Algoritmo ACO");
        executarBtn.addActionListener(this::executarACO);
        add(executarBtn, BorderLayout.SOUTH);
    }

    private void executarACO(ActionEvent e) {
        Map<String, Evento> grafo = AppSwing.criarGrafo();  // reutiliza AppSwing
        ACO aco = new ACO(grafo);
        List<Evento> melhorCaminho = aco.encontrarMelhorCaminho("Login / Autenticação", "Download PDF");

        if (melhorCaminho == null || melhorCaminho.isEmpty()) {
            resultadoArea.setText("Nenhum caminho encontrado.");
        } else {
            StringBuilder sb = new StringBuilder("Melhor Caminho:\n\n");
            for (int i = 0; i < melhorCaminho.size(); i++) {
                sb.append("• ").append(melhorCaminho.get(i).getNome()).append("\n");
                if (i < melhorCaminho.size() - 1) sb.append("   ↓\n");
            }
            resultadoArea.setText(sb.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}