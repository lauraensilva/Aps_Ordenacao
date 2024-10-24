package projeto_aps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private Connection conexao;

    public Conexao() {
        try {
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/aps_4p", "root", "");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public Connection Conectar() {
        return conexao;
    }
}
