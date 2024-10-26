package projeto_aps;

public class QuickSort {
    public Object[][] ordenar(Object[][] dados, int colunaParaOrdenar) {
        int quantidadeDados = dados.length; // Quantidade de dados a serem ordenados
        long tempoExecucao;

        long inicio = System.nanoTime(); // Marca o início da ordenação

        // Algoritmo de Quick Sort
        quickSort(dados, 0, quantidadeDados - 1, colunaParaOrdenar);

        long fim = System.nanoTime(); // Marca o fim da ordenação
        tempoExecucao = fim - inicio; // Calcula o tempo total de execução

        // Chama o método de impressão
        String tipoOrdenacao = (colunaParaOrdenar == 1) ? "id_aleatorio" : "id_semiordenado";
        OrdenacaoUtils.imprimirMatriz("Quick Sort", tempoExecucao, quantidadeDados, tipoOrdenacao);
        return dados;
    }

    private void quickSort(Object[][] dados, int low, int high, int colunaParaOrdenar) {
        if (low < high) {
            int pi = partition(dados, low, high, colunaParaOrdenar);
            quickSort(dados, low, pi - 1, colunaParaOrdenar);
            quickSort(dados, pi + 1, high, colunaParaOrdenar);
        }
    }

    private int partition(Object[][] dados, int low, int high, int colunaParaOrdenar) {
        Object[] pivot = dados[high]; // Pivô
        int i = (low - 1); // Índice do menor elemento

        for (int j = low; j < high; j++) {
            if ((int) dados[j][colunaParaOrdenar] < (int) pivot[colunaParaOrdenar]) {
                i++;
                Object[] temp = dados[i];
                dados[i] = dados[j];
                dados[j] = temp;
            }
        }

        Object[] temp = dados[i + 1];
        dados[i + 1] = dados[high];
        dados[high] = temp;

        return i + 1;
    }
}