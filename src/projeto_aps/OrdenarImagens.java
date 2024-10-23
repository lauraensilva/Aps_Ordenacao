package projeto_aps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrdenarImagens extends JFrame {

    public OrdenarImagens() {
        // Configurações da janela
        setTitle("Tela de Ordenação");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1)); 

       
        JLabel label = new JLabel("Escolha o método de ordenação dos dados", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14)); 

        
        JButton buttonInsertionSort = new JButton("Insertion Sort");
        JButton buttonMergeSort = new JButton("Merge Sort");
        JButton buttonQuickSort = new JButton("Quick Sort");
        JButton buttonVoltar = new JButton("Voltar");
        JButton buttonSair = new JButton("Sair");

        // Adicionando ActionListener para cada botão
        buttonInsertionSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação para Insertion Sort
                JOptionPane.showMessageDialog(null, "Insertion Sort selecionado!");
            }
        });

        buttonMergeSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação para Merge Sort
                JOptionPane.showMessageDialog(null, "Merge Sort selecionado!");
            }
        });

        buttonQuickSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação para Quick Sort
                JOptionPane.showMessageDialog(null, "Quick Sort selecionado!");
            }
        });

        buttonVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaPrincipal();
                dispose();
            }
        });

        buttonSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação para Sair
                System.exit(0); // Fecha a aplicação
            }
        });

        // Adicionando a label e os botões à janela
        add(label); // Adiciona a label
        add(buttonInsertionSort);
        add(buttonMergeSort);
        add(buttonQuickSort);

        // Criação de um painel para os botões Voltar e Sair
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout()); // Layout horizontal para Voltar e Sair
        panel.add(buttonVoltar);
        panel.add(buttonSair);
        
        // Adicionando o painel à janela
        add(panel);
    }

    public static void main(String[] args) {
        // Criação e exibição da tela
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	OrdenarImagens tela = new OrdenarImagens();
                tela.setVisible(true);
            }
        });
    }
}
