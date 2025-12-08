package sn.brasilburger.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/brasil_burger";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Passer@123$#"; 

    private static Connection connection;

    private DbConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connexion PostgreSQL réussie !");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur de connexion à PostgreSQL");
            e.printStackTrace();
        }
        return connection;
    }
}
