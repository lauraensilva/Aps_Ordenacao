package projeto_aps;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Login extends JFrame {

    private CardLayout cardLayout;
    private JPanel panelContainer;

    private JTextField txtLoginUser;
    private JPasswordField txtLoginPassword;

    private JTextField txtCadastroUser;
    private JPasswordField txtCadastroPassword;

    private HashMap<String, String> usuariosCadastrados;

    public Login() {
        usuariosCadastrados = new HashMap<>();

        setTitle("Login e Cadastro de Usuários");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelContainer = new JPanel(cardLayout);

        panelContainer.add(telaLogin(), "login");
        panelContainer.add(telaCadastro(), "cadastro");

        cardLayout.show(panelContainer, "login");

        add(panelContainer);

        setVisible(true);
    }

    private JPanel telaLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblLogin = new JLabel("Login");
        JLabel lblPassword = new JLabel("Senha");
        txtLoginUser = new JTextField(15);
        txtLoginPassword = new JPasswordField(15);

        JButton btnEntrar = new JButton("Entrar");
        JButton btnCadastrar = new JButton("Cadastrar");

        btnCadastrar.addActionListener(e -> cardLayout.show(panelContainer, "cadastro"));

        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = txtLoginUser.getText();
                String senha = new String(txtLoginPassword.getPassword());

                if (usuariosCadastrados.containsKey(usuario) && usuariosCadastrados.get(usuario).equals(senha)) {
                    JOptionPane.showMessageDialog(null, "Login bem-sucedido!");

                    // Chama a nova tela após o login
                    new TelaPrincipal();
                    dispose();  // Fecha a tela de login/cadastro
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!");
                }
            }
        });

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(lblLogin, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(txtLoginUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(lblPassword, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(txtLoginPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(btnEntrar, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; panel.add(btnCadastrar, gbc);

        return panel;
    }

    private JPanel telaCadastro() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblCadastro = new JLabel("Novo Usuário");
        JLabel lblSenha = new JLabel("Nova Senha");
        txtCadastroUser = new JTextField(15);
        txtCadastroPassword = new JPasswordField(15);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltarLogin = new JButton("Voltar");

        btnVoltarLogin.addActionListener(e -> cardLayout.show(panelContainer, "login"));

        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String novoUsuario = txtCadastroUser.getText();
                String novaSenha = new String(txtCadastroPassword.getPassword());

                if (usuariosCadastrados.containsKey(novoUsuario)) {
                    JOptionPane.showMessageDialog(null, "Usuário já cadastrado!");
                } else {
                    usuariosCadastrados.put(novoUsuario, novaSenha);
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                    cardLayout.show(panelContainer, "login");
                }
            }
        });

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(lblCadastro, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(txtCadastroUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(lblSenha, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(txtCadastroPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(btnSalvar, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; panel.add(btnVoltarLogin, gbc);

        return panel;
    }

    public static void main(String[] args) {
        new Login();
    }
}
