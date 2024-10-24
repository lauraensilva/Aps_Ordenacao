package projeto_aps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdenarImagens extends JFrame {
    private Consultas consulta;
    private ResultSet resultSet;
    private Object[][] dados;
    private Object[][] dadosOrdenados;

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
        JButton buttonBubbleSort = new JButton("Bubble Sort");
        JButton buttonQuickSort = new JButton("Quick Sort");
        JButton buttonVoltar = new JButton("Voltar");
        JButton buttonSair = new JButton("Sair");

        // Adicionando ActionListener para cada botão
        buttonInsertionSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Busca os dados
                dados = pegarDados();

                // Pergunta ao usuário por qual coluna ele deseja ordenar
                String[] opcoes = { "id_aleatorio", "id_semialeatorio" };
                int escolha = JOptionPane.showOptionDialog(null, "Escolha a coluna para ordenar:", "Opção de Ordenação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

                // 0 = id_aleatorio, 1 = id_semialeatorio
                int colunaParaOrdenar;
                if (escolha == 0) {
                    colunaParaOrdenar = 1; // id_aleatorio
                } else if (escolha == 1) {
                    colunaParaOrdenar = 2; // id_semialeatorio
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhuma opção válida foi selecionada.");
                    return; // Encerra a ação se não foi selecionada uma opção
                }

                // Cria a instância de InsertionSort e ordena os dados
                InsertionSort insertionSort = new InsertionSort();
                dadosOrdenados = insertionSort.ordenar(dados, colunaParaOrdenar);
                new VisualizarOrdenacao(dadosOrdenados);
            }
        });

        buttonBubbleSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dados = pegarDados();

                // Pergunta ao usuário por qual coluna ele deseja ordenar
                String[] opcoes = { "id_aleatorio", "id_semialeatorio" };
                int escolha = JOptionPane.showOptionDialog(null, "Escolha a coluna para ordenar:", "Opção de Ordenação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

                // 0 = id_aleatorio, 1 = id_semialeatorio
                int colunaParaOrdenar;
                if (escolha == 0) {
                    colunaParaOrdenar = 1; // id_aleatorio
                } else if (escolha == 1) {
                    colunaParaOrdenar = 2; // id_semialeatorio
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhuma opção válida foi selecionada.");
                    return; // Encerra a ação se não foi selecionada uma opção
                }

                BubbleSort bubbleSort = new BubbleSort();
                dadosOrdenados = bubbleSort.ordenar(dados, colunaParaOrdenar);
                new VisualizarOrdenacao(dadosOrdenados);
            }
        });

        buttonQuickSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dados = pegarDados();

                // Pergunta ao usuário por qual coluna ele deseja ordenar
                String[] opcoes = { "id_aleatorio", "id_semialeatorio" };
                int escolha = JOptionPane.showOptionDialog(null, "Escolha a coluna para ordenar:", "Opção de Ordenação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

                // 0 = id_aleatorio, 1 = id_semialeatorio
                int colunaParaOrdenar;
                if (escolha == 0) {
                    colunaParaOrdenar = 1; // id_aleatorio
                } else if (escolha == 1) {
                    colunaParaOrdenar = 2; // id_semialeatorio
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhuma opção válida foi selecionada.");
                    return; // Encerra a ação se não foi selecionada uma opção
                }

                QuickSort quickSort = new QuickSort();
                dadosOrdenados = quickSort.ordenar(dados, colunaParaOrdenar);
                new VisualizarOrdenacao(dadosOrdenados);
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
        add(buttonBubbleSort);
        add(buttonQuickSort);

        // Criação de um painel para os botões Voltar e Sair
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout()); // Layout horizontal para Voltar e Sair
        panel.add(buttonVoltar);
        panel.add(buttonSair);

        // Adicionando o painel à janela
        add(panel);
        setVisible(true);
    }

    private Object[][] pegarDados() {
        String sql = "SELECT id, id_aleatorio, id_semialeatorio, nome_imagem, localizacao, data FROM imagens";
        consulta = new Consultas(sql, null, null, null, null, "DADOS");

        try {
            resultSet = consulta.buscarDados();
            resultSet.last();

            int totalLinhas = resultSet.getRow();
            resultSet.beforeFirst();

            int quantidadeColunas = 6;

            Object[][] matriz = new Object[totalLinhas][quantidadeColunas];
            int row = 0;
            while (resultSet.next()) {
                matriz[row][0] = resultSet.getInt("id");
                matriz[row][1] = resultSet.getInt("id_aleatorio");
                matriz[row][2] = resultSet.getInt("id_semialeatorio");
                matriz[row][3] = resultSet.getString("nome_imagem");
                matriz[row][4] = resultSet.getString("localizacao");
                matriz[row][5] = resultSet.getString("data");
                row++;
            }

            return matriz;
        } catch (SQLException e) {
            System.out.println("Erro ao buscar dados " + e.getMessage());
            return null;
        }
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
