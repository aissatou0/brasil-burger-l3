package sn.brasilburger.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    private static final String URL = "jdbc:postgresql://ep-red-shadow-ab3xwugz-pooler.eu-west-2.aws.neon.tech/neondb?sslmode=require";
    private static final String USER = "neondb_owner";
    private static final String PASSWORD = "npg_I8HmUrAotiM7";  

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("🌍 Connexion NEON réussie !");
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur connexion NEON");
            e.printStackTrace();
        }
        return connection;
    }
}
