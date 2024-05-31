package wahidiyanpost;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class koneksi {
    Connection conn = null;
    public static Connection koneksi(){
        String driver = "com.mysql.jdbc.Driver";
        String host = "jdbc:mysql://localhost/db_wahidiyanpost";
        String user = "root";
        String password = "";
        
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(host, user, password);
            Alert peringatan = new Alert(Alert.AlertType.INFORMATION);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            Alert peringatan = new Alert(Alert.AlertType.ERROR);
            peringatan.setTitle("Peringatan SQL Connection");
            peringatan.setHeaderText(null);
            peringatan.setContentText("" + e);
            peringatan.showAndWait();
            Platform.exit();
        }
        return null;
    }
}
