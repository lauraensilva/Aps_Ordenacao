package projeto_aps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroImagem extends JFrame {

    private JTextField txtNomeImagem;
    private JTextField txtLocalizacao;
    private JLabel labelPreview;
    private File imagemSelecionada;

    public CadastroImagem() {
        setTitle("Cadastro de Imagens");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblNome = new JLabel("Nome da Imagem:");
        JLabel lblLocalizacao = new JLabel("Localização:");

        txtNomeImagem = new JTextField(20);
        txtLocalizacao = new JTextField(20);

        JButton btnSelecionarImagem = new JButton("Selecionar Imagem");
        JButton btnUpload = new JButton("Fazer Upload");

        labelPreview = new JLabel("Pré-visualização da Imagem");
        labelPreview.setPreferredSize(new Dimension(200, 200));
        labelPreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Ação do botão Selecionar Imagem
        btnSelecionarImagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setDialogTitle("Selecionar uma imagem");  // Título da janela do explorador
                fileChooser.setAcceptAllFileFilterUsed(false);  // Não aceitar todos os tipos de arquivos
                fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png", "bmp", "gif"));

                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    imagemSelecionada = fileChooser.getSelectedFile();
                    String imagePath = imagemSelecionada.getAbsolutePath();
                    txtLocalizacao.setText(imagePath);  // Definir o caminho no campo de localização
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
                    labelPreview.setIcon(imageIcon);
                }
            }
        });

        // Ação do botão Fazer Upload
        btnUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (imagemSelecionada == null || txtNomeImagem.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Selecione uma imagem e preencha o nome.");
                } else {
                    // Função para salvar a imagem no banco de dados
                    salvarImagemNoBanco(txtNomeImagem.getText(), txtLocalizacao.getText(), imagemSelecionada);
                }
            }
        });

        // Layout
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblNome, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtNomeImagem, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(lblLocalizacao, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtLocalizacao, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnSelecionarImagem, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(labelPreview, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(btnUpload, gbc);

        setVisible(true);
    }

    // Método para salvar a imagem no banco de dados
    private void salvarImagemNoBanco(String nome_imagem, String localizacao, File imagem) {
        Connection conexao = null;
        PreparedStatement statement = null;

        try {
            // Estabelecendo a conexão com o banco de dados
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/aps_4p", "root", "Laura1020@");

            String sql = "INSERT INTO imagens_aux (nome_imagem, localizacao, imagem) VALUES (?, ?, ?)";
            statement = conexao.prepareStatement(sql);

            statement.setString(1, nome_imagem);
            statement.setString(2, localizacao.isEmpty() ? null : localizacao); // Permitir valor nulo para localização

            // Converte a imagem para um InputStream e salva como BLOB
            FileInputStream fis = new FileInputStream(imagem);
            statement.setBinaryStream(3, fis, (int) imagem.length());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Imagem carregada com sucesso!");
            }
        } catch (SQLException | java.io.IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao salvar imagem: " + ex.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
                if (conexao != null) conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new CadastroImagem();
    }
}
