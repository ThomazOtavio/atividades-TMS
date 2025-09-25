/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kensl
 */

/**
 * Classe principal com interface Swing para executar ACO + Greedy em um grafo de eventos.
 */
public class AppSwing extends JFrame {
//    private JTextArea resultadoArea;
    private JTable tabelaResultado;
    private DefaultTableModel tabelaModel;
    private JComboBox<String> comboInicio;
    private JComboBox<String> comboDestino;
    private Map<String, Evento> grafo;

    public AppSwing() {
        super("ACO - Navegação App de Eventos Escolar");

        // Inicializa o grafo
        grafo = criarGrafo();

        // Configuração da interface
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Simulação de Navegação Otimizada (ACO)", JLabel.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Painel de seleção de início e destino
        JPanel painelSelecao = new JPanel(new FlowLayout());
        painelSelecao.add(new JLabel("Início:"));
        comboInicio = new JComboBox<>();
        painelSelecao.add(comboInicio);
        painelSelecao.add(new JLabel("Destino:"));
        comboDestino = new JComboBox<>();
        painelSelecao.add(comboDestino);

        // Preenche os combos com os nomes dos eventos
        List<String> nomesOrdenados = new ArrayList<>(grafo.keySet());
        Collections.sort(nomesOrdenados); // ordena em ordem alfabética

        for (String nomeEvento : nomesOrdenados) {
            comboInicio.addItem(nomeEvento);
            comboDestino.addItem(nomeEvento);
        }

        add(painelSelecao, BorderLayout.NORTH);

        String[] colunas = {"Evento"};
        tabelaModel = new DefaultTableModel(colunas, 0);
        tabelaResultado = new JTable(tabelaModel);
        JScrollPane scroll = new JScrollPane(tabelaResultado);
        add(scroll, BorderLayout.CENTER);
           
        JButton btnExecutar = new JButton("Executar ACO");
        btnExecutar.addActionListener(this::executarACO);
        add(btnExecutar, BorderLayout.SOUTH);
    }

    private void executarACO(ActionEvent e) {
        String inicio = (String) comboInicio.getSelectedItem();
        String destino = (String) comboDestino.getSelectedItem();


       if (inicio.equals(destino)) {
            MensagemResultado.mostrarDestinoIgual(tabelaResultado, tabelaModel);
            return;
        }

        ACO aco = new ACO(grafo);
        List<Evento> caminho = aco.encontrarMelhorCaminho(inicio, destino);
        
        tabelaModel.setRowCount(0); // limpa a tabela

        if (caminho == null || caminho.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Nenhum caminho encontrado entre " + inicio + " e " + destino + ".",
                "Caminho não encontrado", JOptionPane.WARNING_MESSAGE);
        } else {
            for (Evento evento : caminho) {
                tabelaModel.addRow(new Object[]{evento.getNome()});
            }
        }
        
    }

    public static Map<String, Evento> criarGrafo() {
        Map<String, Evento> eventos = new HashMap<>();
        
        // Criação dos 35 eventos
        String[] nomes = {
            "Login", "Menu Principal", "Perfil", "Notas", "Faltas", "Horários", "Biblioteca", "Empréstimos",
            "Reservas", "Renovação", "Disciplinas", "Grade", "Histórico", "Mensagens", "Notificações", "Eventos",
            "Calendário", "Estágio", "Boletim", "Carteirinha", "QR Code", "Pagamentos", "Financeiro",
            "2ª Via Boleto", "Solicitações", "Documentos", "Matrícula", "Rematrícula", "Atendimento", "FAQ",
            "Suporte", "Fórum", "Configurações", "Segurança", "Sair"
        };

        for (String nome : nomes) {
            eventos.put(nome, new Evento(nome));
        }
        
        
        // Adicionando as 70 arestas direcionadas
        eventos.get("Login").adicionarVizinho(eventos.get("Menu Principal"));
        eventos.get("Menu Principal").adicionarVizinho(eventos.get("Perfil"));
        eventos.get("Menu Principal").adicionarVizinho(eventos.get("Notas"));
        eventos.get("Menu Principal").adicionarVizinho(eventos.get("Faltas"));
        eventos.get("Menu Principal").adicionarVizinho(eventos.get("Horários"));
        eventos.get("Menu Principal").adicionarVizinho(eventos.get("Biblioteca"));
        eventos.get("Menu Principal").adicionarVizinho(eventos.get("Disciplinas"));
        eventos.get("Menu Principal").adicionarVizinho(eventos.get("Mensagens"));
        eventos.get("Perfil").adicionarVizinho(eventos.get("Configurações"));
        eventos.get("Notas").adicionarVizinho(eventos.get("Histórico"));
        eventos.get("Faltas").adicionarVizinho(eventos.get("Histórico"));
        eventos.get("Horários").adicionarVizinho(eventos.get("Grade"));
        eventos.get("Biblioteca").adicionarVizinho(eventos.get("Empréstimos"));
        eventos.get("Biblioteca").adicionarVizinho(eventos.get("Reservas"));
        eventos.get("Empréstimos").adicionarVizinho(eventos.get("Renovação"));
        eventos.get("Reservas").adicionarVizinho(eventos.get("Renovação"));
        eventos.get("Renovação").adicionarVizinho(eventos.get("Biblioteca"));
        eventos.get("Disciplinas").adicionarVizinho(eventos.get("Grade"));
        eventos.get("Disciplinas").adicionarVizinho(eventos.get("Notas"));
        eventos.get("Disciplinas").adicionarVizinho(eventos.get("Faltas"));
        eventos.get("Grade").adicionarVizinho(eventos.get("Boletim"));
        eventos.get("Histórico").adicionarVizinho(eventos.get("Boletim"));
        eventos.get("Mensagens").adicionarVizinho(eventos.get("Notificações"));
        eventos.get("Notificações").adicionarVizinho(eventos.get("Eventos"));
        eventos.get("Eventos").adicionarVizinho(eventos.get("Calendário"));
        eventos.get("Calendário").adicionarVizinho(eventos.get("Eventos"));
        eventos.get("Estágio").adicionarVizinho(eventos.get("Solicitações"));
        eventos.get("Estágio").adicionarVizinho(eventos.get("Documentos"));
        eventos.get("Boletim").adicionarVizinho(eventos.get("Matrícula"));
        eventos.get("Carteirinha").adicionarVizinho(eventos.get("QR Code"));
        eventos.get("QR Code").adicionarVizinho(eventos.get("Carteirinha"));
        eventos.get("Pagamentos").adicionarVizinho(eventos.get("Financeiro"));
        eventos.get("Financeiro").adicionarVizinho(eventos.get("2ª Via Boleto"));
        eventos.get("2ª Via Boleto").adicionarVizinho(eventos.get("Pagamentos"));
        eventos.get("Solicitações").adicionarVizinho(eventos.get("Documentos"));
        eventos.get("Documentos").adicionarVizinho(eventos.get("Histórico"));
        eventos.get("Matrícula").adicionarVizinho(eventos.get("Rematrícula"));
        eventos.get("Rematrícula").adicionarVizinho(eventos.get("Histórico"));
        eventos.get("Atendimento").adicionarVizinho(eventos.get("FAQ"));
        eventos.get("FAQ").adicionarVizinho(eventos.get("Suporte"));
        eventos.get("Suporte").adicionarVizinho(eventos.get("Fórum"));     
        eventos.get("Fórum").adicionarVizinho(eventos.get("Atendimento"));



        eventos.get("Fórum").adicionarVizinho(eventos.get("Boletim"));

        

        eventos.get("Configurações").adicionarVizinho(eventos.get("Segurança"));
        eventos.get("Segurança").adicionarVizinho(eventos.get("Sair"));
        eventos.get("Sair").adicionarVizinho(eventos.get("Login"));
        eventos.get("Menu Principal").adicionarVizinho(eventos.get("Pagamentos"));
        eventos.get("Menu Principal").adicionarVizinho(eventos.get("Estágio"));
        eventos.get("Menu Principal").adicionarVizinho(eventos.get("Solicitações"));
        eventos.get("Perfil").adicionarVizinho(eventos.get("Mensagens"));
        eventos.get("Notas").adicionarVizinho(eventos.get("Boletim"));
        eventos.get("Faltas").adicionarVizinho(eventos.get("Boletim"));
        eventos.get("Horários").adicionarVizinho(eventos.get("Disciplinas"));
        eventos.get("Biblioteca").adicionarVizinho(eventos.get("Carteirinha"));
        eventos.get("Empréstimos").adicionarVizinho(eventos.get("Documentos"));
        eventos.get("Reservas").adicionarVizinho(eventos.get("Biblioteca"));
        eventos.get("Renovação").adicionarVizinho(eventos.get("Notas"));
        eventos.get("Disciplinas").adicionarVizinho(eventos.get("Matrícula"));
        eventos.get("Grade").adicionarVizinho(eventos.get("Matrícula"));
        eventos.get("Histórico").adicionarVizinho(eventos.get("Financeiro"));
        eventos.get("Mensagens").adicionarVizinho(eventos.get("Fórum"));
        eventos.get("Notificações").adicionarVizinho(eventos.get("Mensagens"));
        eventos.get("Eventos").adicionarVizinho(eventos.get("Atendimento"));
        eventos.get("Calendário").adicionarVizinho(eventos.get("Fórum"));
        eventos.get("Estágio").adicionarVizinho(eventos.get("Menu Principal"));
        eventos.get("Boletim").adicionarVizinho(eventos.get("Menu Principal"));
        eventos.get("Carteirinha").adicionarVizinho(eventos.get("Menu Principal"));
        eventos.get("QR Code").adicionarVizinho(eventos.get("Menu Principal"));
        eventos.get("Pagamentos").adicionarVizinho(eventos.get("Menu Principal"));
        eventos.get("Financeiro").adicionarVizinho(eventos.get("Menu Principal"));
        eventos.get("2ª Via Boleto").adicionarVizinho(eventos.get("Menu Principal"));
        eventos.get("Solicitações").adicionarVizinho(eventos.get("Menu Principal"));
        
        
        //Eventos Adicionais
       

        return eventos;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppSwing app = new AppSwing();
            app.setVisible(true);
        });
    }
}

