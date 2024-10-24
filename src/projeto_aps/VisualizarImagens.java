package projeto_aps;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VisualizarImagens extends JFrame {
    private Consultas consulta;
    private ResultSet dados;

    private JTable table;
    private DefaultTableModel tableModel;

    public VisualizarImagens() {
        setTitle("Imagens Cadastradas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criação da tabela e do modelo
        String[] colunas = {"ID","ID Aleatório","ID Semiordenado", "Nome da Imagem", "Localização", "Data de Cadastro"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desabilitar edição das células
            }
          
        };
        table = new JTable(tableModel);
        table.setRowHeight(20); // Altura da linha para exibir imagens

        // ScrollPane para a tabela
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Buscar dados do banco e preencher a tabela
        carregarDadosDoBanco();

        setVisible(true);
    }

    private void carregarDadosDoBanco() {

        try {
            String sql = "SELECT id, id_aleatorio, id_semialeatorio, nome_imagem, localizacao, data FROM imagens";
            consulta = new Consultas(sql, null, null, null, null, "DADOS");

            dados = consulta.buscarDados();

            // Processar os dados retornados
            while (dados.next()) {
                int id = dados.getInt("id");
                int id_aleatorio = dados.getInt("id_aleatorio");
                int id_semiordenado = dados.getInt("id_semialeatorio");
                String nomeImagem = dados.getString("nome_imagem");
                String localizacao = dados.getString("localizacao");
                Timestamp data = dados.getTimestamp("data");

                // Adicionando os dados na tabela
                tableModel.addRow(new Object[]{id, id_aleatorio, id_semiordenado, nomeImagem, localizacao, data});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar dados: " + ex.getMessage());
        } finally {
            consulta.fecharConexao(dados);
        }
    }

    public static void main(String[] args) {
        new VisualizarImagens();
    }
}
