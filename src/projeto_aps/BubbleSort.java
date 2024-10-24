package projeto_aps;

public class BubbleSort {
    public Object[][] ordenar(Object[][] dados, int colunaParaOrdenar) {
        int quantidadeDados = dados.length; // Quantidade de dados a serem ordenados
        long tempoExecucao;

        long inicio = System.nanoTime(); // Marca o início da ordenação

        // Algoritmo de Bubble Sort
        for (int i = 0; i < dados.length - 1; i++) {
            for (int j = 0; j < dados.length - i - 1; j++) {
                if ((int) dados[j][colunaParaOrdenar] > (int) dados[j + 1][colunaParaOrdenar]) {
                    Object[] temp = dados[j];
                    dados[j] = dados[j + 1];
                    dados[j + 1] = temp;
                }
            }
        }

        long fim = System.nanoTime(); // Marca o fim da ordenação
        tempoExecucao = fim - inicio; // Calcula o tempo total de execução

        // Chama o método de impressão
        String tipoOrdenacao = (colunaParaOrdenar == 1) ? "id_aleatorio" : "id_semialeatorio";
        OrdenacaoUtils.imprimirMatriz("Bubble Sort", tempoExecucao, quantidadeDados, tipoOrdenacao);
        return dados;
    }
}

