package domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:h2:./data/victortienda";
    private static final String USUARIO = "";
    private static final String PASSWORD = "";

    public static Connection getConexion() throws SQLException {
            return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
