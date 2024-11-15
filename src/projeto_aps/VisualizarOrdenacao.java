package projeto_aps;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VisualizarOrdenacao {
    
    private JTable table;
    private JFrame frame;

    public VisualizarOrdenacao(Object[][] dados) {
        // Configura a janela
        frame = new JFrame("Dados da Ordenação");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1600, 850);
        frame.setLocationRelativeTo(null);

        // Define as colunas da tabela
        String[] colunas = {"ID", "ID Aleatório", "ID Semiordenado", "Nome da Imagem", "Localização", "Data"};

        // Cria um modelo de tabela e define os dados
        DefaultTableModel model = new DefaultTableModel(dados, colunas);
        table = new JTable(model);
        table.setFillsViewportHeight(true); // Faz a tabela preencher a área visível

        // Adiciona a tabela a um JScrollPane para permitir rolagem
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Configura a janela para ser visível
        frame.setVisible(true);
    }
}

