package projeto_aps;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.io.*;

public class VisualizarImagens extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtID;  // Campo de texto para ID
    private JButton btnVisualizar; // Botão para visualizar
    private JButton btnVoltar; // Botão para voltar
    private JButton btnLimpar;
    private JLabel lblImagem; // Label para exibir a imagem

    public VisualizarImagens() {
        setTitle("Imagens Cadastradas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel superior para os componentes de ID e visualizar
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblID = new JLabel("ID:");
        txtID = new JTextField(10);  // Tamanho do campo de texto
        btnVisualizar = new JButton("Visualizar");
        btnVisualizar.addActionListener(e -> buscarImagemPorID());
        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> {
            txtID.setText("");
            carregarDadosDoBanco();
            lblImagem.setIcon(null); // Limpa a imagem exibida
        });

        panelSuperior.add(lblID);
        panelSuperior.add(txtID);
        panelSuperior.add(btnVisualizar);
        panelSuperior.add(btnLimpar);

        add(panelSuperior, BorderLayout.NORTH);  // Adiciona o painel superior ao topo da janela

        // Criação da tabela e do modelo
        String[] colunas = {"ID", "ID Aleatório", "ID Semiordenado", "Nome da Imagem", "Localização", "Data de Cadastro"};
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

        // Criar botão Voltar
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            new TelaPrincipal();
            dispose(); // Fecha a janela atual
        });

        // Painel para o botão Voltar
        JPanel panelInferior = new JPanel();
        panelInferior.add(btnVoltar);
        add(panelInferior, BorderLayout.SOUTH); // Adiciona o painel inferior abaixo da tabela

        // Painel para a imagem
        lblImagem = new JLabel();
        lblImagem.setHorizontalAlignment(JLabel.CENTER);
        add(lblImagem, BorderLayout.EAST); // Adiciona a label de imagem ao lado da tabela

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

    // Método para buscar a imagem pelo ID informado
    private void buscarImagemPorID() {
        String idTexto = txtID.getText();
        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID deve ser um número inteiro.");
            return;
        }

        // Limpar a tabela antes de carregar novos dados
        tableModel.setRowCount(0);
        lblImagem.setIcon(null); // Limpa a imagem exibida

        Connection conexao = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // Conectar ao banco de dados
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/aps_4p", "root", "Laura1020@");
            // Consulta SQL para buscar os dados do ID informado
            String sql = "SELECT id, id_aleatorio, id_semiordenado, nome_imagem, localizacao, data, imagem FROM imagens WHERE id = ?";
            statement = conexao.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            // Processar os dados retornados
            if (resultSet.next()) {
                int idAleatorio = resultSet.getInt("id_aleatorio");
                int idSemiordenado = resultSet.getInt("id_semiordenado");
                String nomeImagem = resultSet.getString("nome_imagem");
                String localizacao = resultSet.getString("localizacao");
                byte[] imagemBlob = resultSet.getBytes("imagem");
                Timestamp data = resultSet.getTimestamp("data");

                ImageIcon imagemIcon = null;
                if (imagemBlob != null) {
                    BufferedImage imagem = ImageIO.read(new ByteArrayInputStream(imagemBlob));
                    if (imagem != null) {
                        ImageIcon icon = new ImageIcon(imagem);
                        Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
                        imagemIcon = new ImageIcon(scaledImage);
                    }
                }
                // Adicionando os dados na tabela
                tableModel.addRow(new Object[]{id, idAleatorio, idSemiordenado, nomeImagem, localizacao, data});
                lblImagem.setIcon(imagemIcon); // Exibe a imagem no JLabel
            } else {
                JOptionPane.showMessageDialog(this, "Nenhuma imagem encontrada com o ID: " + id);
                lblImagem.setIcon(null); // Limpa a imagem exibida
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar dados: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar a imagem: " + ex.getMessage());
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
