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
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import util.helpers;

/**
 * FXML Controller class
 *
 * @author Lenovo - GK
 */
public class InterfaceTambahBeritaController extends helpers implements Initializable {

    @FXML
    private TextField txtjudul;
    @FXML
    private RadioButton rd1;
    @FXML
    private RadioButton rd2;
    @FXML
    private RadioButton rd3;
    @FXML
    private RadioButton rd4;
    @FXML
    private RadioButton rd5;
    @FXML
    private RadioButton rd6;
    @FXML
    private DatePicker tglberita;
    @FXML
    private TextArea txtisi;
    @FXML
    private Button btnkembali;
    @FXML
    private Button btnsimpan;
    @FXML
    private ToggleGroup jenis;

    PreparedStatement pst = null;
    Connection conn = koneksi.koneksi();
    ResultSet rs = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void kembaliClick(ActionEvent event) {
        changePage(event, "MenuAdminDocument");
    }

    @FXML
    private void simpanClick(ActionEvent event) {
        int penulis = InterfaceLoginController.id_penulis;
        String judul = txtjudul.getText();
        String tanggal = tglberita.getValue().toString();
        String isi = txtisi.getText();
        String selJenis = null;
        RadioButton selectedButton = (RadioButton) jenis.getSelectedToggle();
        if (selectedButton != null) {
            selJenis = selectedButton.getText();
        }

        if (!judul.isEmpty() && !tanggal.isEmpty() && !isi.isEmpty() && !selJenis.isEmpty()) {
            try {
                String query = "INSERT INTO berita VALUES(NULL, ?, ?, ? , ?, ?)";
                pst = conn.prepareStatement(query);
                pst.setInt(1, penulis);
                pst.setString(2, judul);
                pst.setString(3, selJenis);
                pst.setString(4, tanggal);
                pst.setString(5, isi);

                int rowAffected = pst.executeUpdate();

                if (rowAffected > 0) {
                    Alert pemberitahuan = new Alert(Alert.AlertType.INFORMATION);
                    pemberitahuan.setTitle("Tambah Data Berhasil");
                    pemberitahuan.setHeaderText(null);
                    pemberitahuan.setContentText("Berhasil Menambahkan Berita!");
                    pemberitahuan.showAndWait();
                    pemberitahuan.setResizable(false);
                    txtjudul.setText("");
                    tglberita.setValue(LocalDate.now());
                    txtisi.setText("");
                    RadioButton selectedRadio = (RadioButton) jenis.getSelectedToggle();
                    if (selectedRadio != null) {
                        selectedRadio.setSelected(false);
                    }
            
                    changePage(event, "MenuAdminDocument");
                } else {
                    Alert pemberitahuan = new Alert(Alert.AlertType.INFORMATION);
                    pemberitahuan.setTitle("Tambah Data Gagal");
                    pemberitahuan.setHeaderText(null);
                    pemberitahuan.setContentText("Gagal Menambahkan Berita!");
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
    }
}
