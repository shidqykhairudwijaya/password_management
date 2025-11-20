import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static Connection conn;

    public static Connection getKoneksi() {
        if (conn == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/password_management";
                String user = "root";  // sesuaikan
                String pass = "";      // sesuaikan

                conn = DriverManager.getConnection(url, user, pass);
                System.out.println("Koneksi sukses");

            } catch (SQLException e) {
                System.out.println("Koneksi gagal: " + e.getMessage());
            }
        }
        return conn;
    }
}

