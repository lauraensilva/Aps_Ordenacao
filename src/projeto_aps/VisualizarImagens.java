package projeto_aps;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.*;
import javax.imageio.ImageIO;

public class VisualizarImagens extends JFrame {

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
        Connection conexao = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Conectar ao banco de dados
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/aps_4p", "root", "Laura1020@");

            // Consulta SQL para buscar os dados
            String sql = "SELECT id, id_aleatorio, id_semiordenado, nome_imagem, localizacao, data FROM imagens";
            statement = conexao.prepareStatement(sql);
            resultSet = statement.executeQuery();

            // Processar os dados retornados
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_aleatorio = resultSet.getInt("id_aleatorio");
                int id_semiordenado = resultSet.getInt("id_semiordenado");
                String nomeImagem = resultSet.getString("nome_imagem");
                String localizacao = resultSet.getString("localizacao");
                Timestamp data = resultSet.getTimestamp("data");


                // Adicionando os dados na tabela
                tableModel.addRow(new Object[]{id, id_aleatorio, id_semiordenado, nomeImagem, localizacao, data});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar dados: " + ex.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conexao != null) conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new VisualizarImagens();
    }
}
