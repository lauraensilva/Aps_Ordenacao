package projeto_aps;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VisualizarRelatorios extends JFrame {
    private Consultas consulta;
    private ResultSet dados;

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnVoltar;

    public VisualizarRelatorios() {
        setTitle("Histórico de Ordenações");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

       
        String[] colunas = {"ID", "metodo_ordenacao", "tipo_ordenacao", "tempo_execucao", "quantidade_registros"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(20);

     
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

      
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            new TelaPrincipal();
            dispose(); 
        });

       
        JPanel panelInferior = new JPanel();
        panelInferior.add(btnVoltar);
        add(panelInferior, BorderLayout.SOUTH); 

    
        carregarDadosDoBanco();

        setVisible(true);
    }

    private void carregarDadosDoBanco() {
        try {
            String sql = "SELECT id, metodo_ordenacao, tipo_ordenacao, tempo_execucao, quantidade_registros FROM ordenacoes";
            consulta = new Consultas(sql, null, null, null, null, null, "DADOS");

            dados = consulta.buscarDados();

            while (dados.next()) {
                int id = dados.getInt("id");
                String metodoOrdenacao = dados.getString("metodo_ordenacao");
                String tipoOrdenacao = dados.getString("tipo_ordenacao");
                String tempoExecucao = dados.getString("tempo_execucao");
                String quantRegistros = dados.getString("quantidade_registros");

                
                tableModel.addRow(new Object[]{id, metodoOrdenacao, tipoOrdenacao, tempoExecucao, quantRegistros});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar dados: " + ex.getMessage());
        } finally {
            consulta.fecharConexao(dados);
        }
    }

    public static void main(String[] args) {
        new VisualizarRelatorios();
    }
}
