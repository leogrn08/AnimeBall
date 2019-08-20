package animeball.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection("jdbc:mysql://web.farroupilha.ifrs.edu.br:1080/leonardogasperin?useSSL=false", "leonardogasperin", "Canitorup@80");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        Connection con = null;
        con = getConnection();
        if (con != null) {
            System.out.println("Conectado!");

            con.close();
        }
    }
}
