package projeto_aps; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CadastroImagem extends JFrame {
    private Consultas consulta;
    private Boolean cadastrado;

    private JTextField txtNomeImagem;
    private JTextField txtLocalizacao;
    private JLabel labelPreview;
    private File imagemSelecionada;
    private JButton btnVoltar;

    public CadastroImagem() {
        setTitle("Cadastro de Imagens");
        setSize(1600, 850);
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
                fileChooser.setDialogTitle("Selecionar uma imagem");
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png", "bmp", "gif"));

                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    imagemSelecionada = fileChooser.getSelectedFile();
                    String imagePath = imagemSelecionada.getAbsolutePath();
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
        
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            new TelaPrincipal();
            dispose(); 
        });

        // Painel para os botões de Upload e Voltar
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotoes.add(btnUpload);
        panelBotoes.add(btnVoltar);

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
        add(panelBotoes, gbc);  // Adiciona o painel de botões

        setVisible(true);
    }

    // Método para salvar a imagem no banco de dados
    private void salvarImagemNoBanco(String nome_imagem, String localizacao, File imagem) {
        // Estabelecendo a conexão com o banco de dados
        String sql = "INSERT INTO imagens (nome_imagem, localizacao, imagem) VALUES (?, ?, ?)";
        consulta = new Consultas(sql, nome_imagem, localizacao, imagem, null, null, "IMAGEM");

        cadastrado = consulta.foiCadastrado();
        
        if (cadastrado) {
            JOptionPane.showMessageDialog(null, "Imagem cadastrada com sucesso!");
        } else {
            JOptionPane.showConfirmDialog(null, "Não foi possível cadastrar a imagem!");
        }
    }

    public static void main(String[] args) {
        new CadastroImagem();
    }
}
