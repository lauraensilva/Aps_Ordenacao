package projeto_aps;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.io.*;

public class VisualizarImagens extends JFrame {
	private ResultSet resultSet;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtID;  
    private JButton btnVisualizar; 
    private JButton btnVoltar; 
    private JButton btnLimpar;
    private JLabel lblImagem; 

    public VisualizarImagens() {
        setTitle("Imagens Cadastradas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel superior para os componentes de ID e visualizar
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblID = new JLabel("ID:");
        txtID = new JTextField(10);  
        btnVisualizar = new JButton("Visualizar");
        btnVisualizar.addActionListener(e -> buscarImagemPorID());
        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> {
        	tableModel.setRowCount(0);
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
        table.setRowHeight(20); // Altura da linha

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
    	String sql = "SELECT id, id_aleatorio, id_semiordenado, nome_imagem, localizacao, data FROM imagens";
    	Consultas consulta = new Consultas (sql, null, null, null, null, null, "DADOS");
    	resultSet = consulta.buscarDados();
        try {
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
        	consulta.fecharConexao(resultSet);
        }
    }

    // Método para buscar a imagem pelo ID informado
    private void buscarImagemPorID() {
        String idTexto = txtID.getText();
        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID.");
            return;
        }

        // Limpar a tabela antes de carregar novos dados
        tableModel.setRowCount(0);
        lblImagem.setIcon(null); // Limpa a imagem exibida
        
        String sql = "SELECT id, id_aleatorio, id_semiordenado, nome_imagem, localizacao, data, imagem FROM imagens WHERE id = ?";
    	Consultas consulta = new Consultas (sql, idTexto, null, null, null, null, "IMAGEM_ID");
    	resultSet = consulta.buscarDados();
    	
        try {

            // Processar os dados retornados
            if (resultSet.next()) {
            	int id = resultSet.getInt("id");
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
                JOptionPane.showMessageDialog(this, "Nenhuma imagem encontrada com o ID");
                lblImagem.setIcon(null); // Limpa a imagem exibida
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar dados: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar a imagem: " + ex.getMessage());
        } finally {
            consulta.fecharConexao(resultSet);
        }
    }

    public static void main(String[] args) {
        new VisualizarImagens();
    }
}
