/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kensl
 */
public class MensagemResultado {
   public static void mostrarDestinoIgual(JTable tabela, DefaultTableModel modelo) {
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{"Início e destino devem ser diferentes."});
    }
}
