package projeto_aps;

public class InsertionSort {
    public Object[][] ordenar(Object[][] dados, int colunaParaOrdenar) {
        int quantidadeDados = dados.length; // Quantidade de dados a serem ordenados
        long tempoExecucao;

        long inicio = System.nanoTime(); // Marca o início da ordenação

        // Algoritmo de Insertion Sort
        for (int i = 1; i < dados.length; i++) {
            Object[] chave = dados[i];
            int j = i - 1;

            // Compara os valores da coluna de inteiros
            while (j >= 0 && ((int) dados[j][colunaParaOrdenar]) > (int) chave[colunaParaOrdenar]) {
                dados[j + 1] = dados[j];
                j = j - 1;
            }
            dados[j + 1] = chave;
        }

        long fim = System.nanoTime(); // Marca o fim da ordenação
        tempoExecucao = fim - inicio; // Calcula o tempo total de execução

        // Chama o método de impressão
        String tipoOrdenacao = (colunaParaOrdenar == 1) ? "id_aleatorio" : "id_semialeatorio";
        OrdenacaoUtils.imprimirMatriz("Insertion Sort", tempoExecucao, quantidadeDados, tipoOrdenacao);
        return dados;
    }
}