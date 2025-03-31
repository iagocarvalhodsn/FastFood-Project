
package Projeto.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/fastfood";
    private static final String USUARIO = "root";
    private static final String SENHA = "7294684";

    public ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/fastfood", "root", "7294684");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do MySQL não encontrado!", e);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }

    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }

    }
}

