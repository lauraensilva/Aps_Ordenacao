package projeto_aps;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Consultas {
    private ResultSet resultSet;
    private PreparedStatement statement;
    private Boolean imagemCadastrada, dadosCadastrados;
    private FileInputStream fis = null;

    Connection conexao = new Conexao().Conectar();

    public Consultas(String sql, String str1, String str2, File imagem, String str3, String str4, String tipo) {
        try {
            statement = conexao.prepareStatement(sql);

            if (tipo.equals("IMAGEM")) {
                imagemCadastrada = false;
                statement.setString(1, str1);
                statement.setString(2, str2.isEmpty() ? null : str2);

                // Converte a imagem para um InputStream e salva como BLOB
                fis = new FileInputStream(imagem);
                statement.setBinaryStream(3, fis, (int) imagem.length());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) imagemCadastrada = true;
            }

            if (tipo.equals("ORDENACAO")) {
                dadosCadastrados = false;
                statement.setString(1, str1);
                statement.setString(2, str2);
                statement.setString(3, str3);
                statement.setString(4, str4);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) dadosCadastrados = true;
            }

            if (tipo.equals("DADOS")) {
                // Consulta SQL para buscar os dados
                statement = conexao.prepareStatement(sql);
                resultSet = statement.executeQuery();
            }

        } catch (SQLException | java.io.IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (!tipo.equals("DADOS")) {
                try {
                    if (fis != null) fis.close();
                    if (statement != null) statement.close();
                    if (conexao != null) conexao.close();
                } catch (java.io.IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void fecharConexao(ResultSet rs) {
        if (conexao != null && rs != null && statement != null) {
            try {
                rs.close();
                statement.close();
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean foiCadastrado() {
        return imagemCadastrada;
    }

    public Boolean dadosCadastrados() {
        return dadosCadastrados;
    }

    public ResultSet buscarDados() {
        return resultSet;
    }

}
