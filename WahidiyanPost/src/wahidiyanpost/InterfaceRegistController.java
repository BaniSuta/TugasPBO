/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package wahidiyanpost;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import util.helpers;

/**
 * FXML Controller class
 *
 * @author Lenovo - GK
 */
public class InterfaceRegistController extends helpers implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtusername;
    @FXML
    private PasswordField txtpassword;
    @FXML
    private TextField txtkonfirm;
    @FXML
    private PasswordField txtkpassword;
    @FXML
    private Button btnregist;
    @FXML
    private Button btnback;
    @FXML
    private RadioButton rdyes;
    @FXML
    private RadioButton rdno;
    @FXML
    private Label lbkonfirm;
    @FXML
    private ToggleGroup pen;

    PreparedStatement pst = null;
    Connection conn = koneksi.koneksi();
    Statement statement;
    ResultSet rs = null;

    private int limit = 6;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lbkonfirm.setVisible(false);
        txtkonfirm.setVisible(false);

        txtpassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > limit) {
                txtpassword.setText(oldValue);
            }
        });

        txtkpassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > limit) {
                txtkpassword.setText(oldValue);
            }
        });
    }

    @FXML
    private void yesClick(ActionEvent event) {
        lbkonfirm.setVisible(true);
        txtkonfirm.setVisible(true);
    }

    @FXML
    private void noClick(ActionEvent event) {
        lbkonfirm.setVisible(false);
        txtkonfirm.setVisible(false);
    }

    @FXML
    private void registClick(ActionEvent event) {
        String usn = txtusername.getText();
        String pw = txtpassword.getText();
        String kpw = txtkpassword.getText();
        String penulis = null;
        RadioButton selectedButton = (RadioButton) pen.getSelectedToggle();
        if (selectedButton != null) {
            penulis = selectedButton.getText();
        }

        if (!usn.isEmpty() && !pw.isEmpty() && !kpw.isEmpty() && !penulis.isEmpty()) {
            if (pw.equals(kpw)) {

                if (penulis.equals("Iya")) {
                    String kk = txtkonfirm.getText();

                    if (kk.equals("413")) {

                        try {
                            boolean pl = true;
                            String qry = "INSERT INTO akun VALUES(NULL, ?, ?, ?)";
                            pst = conn.prepareStatement(qry);
                            pst.setString(1, usn);
                            pst.setString(2, pw);
                            pst.setBoolean(3, pl);

                            int rowAffected = pst.executeUpdate();

                            if (rowAffected > 0) {
                                Alert pemberitahuan = new Alert(Alert.AlertType.INFORMATION);
                                pemberitahuan.setTitle("Registrasi Berhasil");
                                pemberitahuan.setHeaderText(null);
                                pemberitahuan.setContentText("Anda Berhasil Registrasi!");
                                pemberitahuan.showAndWait();
                                pemberitahuan.setResizable(false);

                                changePage(event, "InterfaceLogin");
                            } else {
                                Alert pemberitahuan = new Alert(Alert.AlertType.INFORMATION);
                                pemberitahuan.setTitle("Registrasi Gagal");
                                pemberitahuan.setHeaderText(null);
                                pemberitahuan.setContentText("Anda Gagal Registrasi!");
                                pemberitahuan.showAndWait();
                                pemberitahuan.setResizable(false);
                            }

                        } catch (SQLException e) {
                            Alert peringatan = new Alert(Alert.AlertType.ERROR);
                            peringatan.setTitle("SQL Connection");
                            peringatan.setHeaderText(null);
                            peringatan.setContentText("force close \n\n" + e);
                            peringatan.showAndWait();
                            peringatan.setResizable(false);
                        }

                    } else if (!kk.equals("413") && !kk.isEmpty()) {
                        Alert peringatan = new Alert(Alert.AlertType.WARNING);
                        peringatan.setTitle("Registrasi Gagal");
                        peringatan.setHeaderText(null);
                        peringatan.setContentText("Kode Konfirmasi Salah!");
                        peringatan.showAndWait();
                    } else {
                        Alert peringatan = new Alert(Alert.AlertType.WARNING);
                        peringatan.setTitle("Registrasi Gagal");
                        peringatan.setHeaderText(null);
                        peringatan.setContentText("Harap Masukkan Kode Konfirmasi!");
                        peringatan.showAndWait();
                    }
                } else {
                    try {
                        boolean pl = false;
                        String qry = "INSERT INTO akun VALUES(NULL, ?, ?, ?)";
                        pst = conn.prepareStatement(qry);
                        pst.setString(1, usn);
                        pst.setString(2, pw);
                        pst.setBoolean(3, pl);

                        int rowAffected = pst.executeUpdate();

                        if (rowAffected > 0) {
                            Alert pemberitahuan = new Alert(Alert.AlertType.INFORMATION);
                            pemberitahuan.setTitle("Registrasi Berhasil");
                            pemberitahuan.setHeaderText(null);
                            pemberitahuan.setContentText("Anda Berhasil Registrasi!");
                            pemberitahuan.showAndWait();
                            pemberitahuan.setResizable(false);

                            changePage(event, "InterfaceLogin");
                        } else {
                            Alert pemberitahuan = new Alert(Alert.AlertType.INFORMATION);
                            pemberitahuan.setTitle("Registrasi Gagal");
                            pemberitahuan.setHeaderText(null);
                            pemberitahuan.setContentText("Anda Gagal Registrasi!");
                            pemberitahuan.showAndWait();
                            pemberitahuan.setResizable(false);
                        }

                    } catch (SQLException e) {
                        Alert peringatan = new Alert(Alert.AlertType.ERROR);
                        peringatan.setTitle("SQL Connection");
                        peringatan.setHeaderText(null);
                        peringatan.setContentText("force close \n\n" + e);
                        peringatan.showAndWait();
                        peringatan.setResizable(false);
                    }
                }

            } else {
                Alert peringatan = new Alert(Alert.AlertType.WARNING);
                peringatan.setTitle("Registrasi Gagal");
                peringatan.setHeaderText(null);
                peringatan.setContentText("Password dan Konfirmasi Password Tidak Sama!");
                peringatan.showAndWait();
            }
        } else {
            Alert peringatan = new Alert(Alert.AlertType.WARNING);
            peringatan.setTitle("Registrasi Gagal");
            peringatan.setHeaderText(null);
            peringatan.setContentText("Harap Inputkan Semua Data!");
            peringatan.showAndWait();
        }
    }

    @FXML
    private void backClick(ActionEvent event) {
        changePage(event, "InterfaceLogin");
    }

}
