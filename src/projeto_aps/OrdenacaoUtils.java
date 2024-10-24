package projeto_aps;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

public class OrdenacaoUtils {
    public static void imprimirMatriz(String metodoOrdenacao, long tempoExecucao, int quantidadeDados,
            String tipoOrdenacao) {
        // Converte o tempo de execução de nanosegundos para milissegundos
        long tempoEmMillis = TimeUnit.NANOSECONDS.toMillis(tempoExecucao);

        // Converte milissegundos em horas, minutos e segundos
        long minutos = TimeUnit.MILLISECONDS.toMinutes(tempoEmMillis);
        long segundos = TimeUnit.MILLISECONDS.toSeconds(tempoEmMillis) - (minutos * 60);
        long milissegundos = tempoEmMillis - (TimeUnit.MILLISECONDS.toSeconds(tempoEmMillis) * 1000);

        String strTempoExecucao = String.format("%02d:%02d.%03d", minutos, segundos, milissegundos);

        String sql = "INSERT INTO ordenacoes (metodo_ordenacao, tipo_ordenacao, tempo_execucao) VALUES (?, ?, ?)";
        Consultas consulta = new Consultas(sql, metodoOrdenacao, tipoOrdenacao, null, strTempoExecucao, "ORDENACAO");
        Boolean cadastrados = consulta.dadosCadastrados();

        if (cadastrados) {
            JOptionPane.showMessageDialog(null, "Dados de ordenação cadastrados com sucesso!");
        }

        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Ordenação concluída com sucesso!").append("\n\n");
        mensagem.append("Método de ordenação: ").append(metodoOrdenacao).append("\n");
        mensagem.append("Tipo de ordenação: ").append(tipoOrdenacao).append("\n");
        mensagem.append("Quantidade de dados ordenados: ").append(quantidadeDados).append("\n");
        mensagem.append("Tempo de execução: ").append(strTempoExecucao);

        // Exibe a mensagem em uma caixa de diálogo
        JOptionPane.showMessageDialog(null, mensagem.toString(), "Resultados da Ordenação",
                JOptionPane.INFORMATION_MESSAGE);

    }
}
