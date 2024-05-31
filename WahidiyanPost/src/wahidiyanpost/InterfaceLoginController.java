/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package wahidiyanpost;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.helpers;

/**
 * FXML Controller class
 *
 * @author Lenovo - GK
 */
public class InterfaceLoginController extends helpers implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    private TextField txtusername;
    @FXML
    private PasswordField txtpassword;
    @FXML
    private Button btnlogin;
    @FXML
    private Label lb1;
    
    PreparedStatement pst = null;
    Connection conn = koneksi.koneksi();
    Statement statement;
    ResultSet rs = null;
    
    public static int id_penulis;
    public static boolean isPenulis;
    
    private int limit = 6;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtpassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > limit) {
                txtpassword.setText(oldValue);
            }
        });
    }    
    
    @FXML
    private void loginClick(ActionEvent event) {
        String un = txtusername.getText();
        String pw = txtpassword.getText();

        if (!un.isEmpty() || !pw.isEmpty()) {
            try {
                String query = "SELECT * FROM akun WHERE username = ? AND password = ? LIMIT 1";
                pst = conn.prepareStatement(query);
                pst.setString(1, un);
                pst.setString(2, pw);

                rs = pst.executeQuery();

                if (rs.next()) {
                    Alert peringatan = new Alert(Alert.AlertType.INFORMATION);
                    peringatan.setTitle("Login success");
                    peringatan.setHeaderText(null);
                    peringatan.setContentText("SELAMAT DATANG " + un.toUpperCase());
                    peringatan.showAndWait();
                    
                    id_penulis = rs.getInt("id_akun");
                    isPenulis = rs.getBoolean("penulis");
                    
                    changePage(event, "MenuAdminDocument");
                } else {
                    Alert peringatan = new Alert(Alert.AlertType.ERROR);
                    peringatan.setTitle("Peringatan username dan password");
                    peringatan.setHeaderText(null);
                    peringatan.setContentText("Username atau password anda salah");
                    peringatan.showAndWait();
                }
                pst.close();
                rs.close();
            } catch (SQLException e) {
                Alert peringatan = new Alert(Alert.AlertType.ERROR);
                peringatan.setTitle("SQL Connection");
                peringatan.setHeaderText(null);
                peringatan.setContentText("force close \n\n" + e);
                peringatan.showAndWait();
                peringatan.setResizable(false);
            }
        } else {
            Alert peringatan = new Alert(Alert.AlertType.WARNING);
            peringatan.setTitle("Warning");
            peringatan.setHeaderText(null);
            peringatan.setContentText("Harap isi username atau password anda!");
            peringatan.showAndWait();
        }
    }
    
    @FXML
    private void registClick(MouseEvent event){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/InterfaceRegist.fxml"));
        } catch (IOException e){
            e.printStackTrace();
        }
        
        stage.setScene(new Scene(root));
        stage.show();
    } 
   
}
