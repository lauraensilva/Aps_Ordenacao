package projeto_aps;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Menu Principal");
        setSize(1600, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(7, 1));

        JLabel label = new JLabel("O que deseja fazer?", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        // Botões
        JButton btnCadastrarImagens = new JButton("Cadastrar Imagens");
        JButton btnVisualizarImagens = new JButton("Visualizar Imagens Cadastradas");
        JButton btnOrdenarImagens = new JButton("Ordenar Imagens");
        JButton btnVisualizarRelatorios = new JButton("Visualizar Relatórios");
        JButton buttonVoltar = new JButton("Voltar");
        JButton buttonSair = new JButton("Sair");

        // Ações dos botões (por enquanto apenas exibe uma mensagem)
        btnCadastrarImagens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new CadastroImagem();
                dispose();
            }
        });

        btnVisualizarImagens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VisualizarImagens();
                dispose();
            }
        });

        btnOrdenarImagens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrdenarImagens();
                dispose();
            }
        });

        btnVisualizarRelatorios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VisualizarRelatorios();
                dispose();
            }
        });
        
        buttonVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });

        buttonSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); 
            }
        });

        add(label); // Adiciona a label
        add(btnCadastrarImagens);
        add(btnVisualizarImagens);
        add(btnOrdenarImagens);
        add(btnVisualizarRelatorios);

        // Criação de um painel para os botões Voltar e Sair
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout()); // Layout horizontal para Voltar e Sair
        panel.add(buttonVoltar);
        panel.add(buttonSair);
        
        // Adicionando o painel à janela
        add(panel);
        setVisible(true);
    }
    public static void main(String[] args) {
        new TelaPrincipal();
    }
}
