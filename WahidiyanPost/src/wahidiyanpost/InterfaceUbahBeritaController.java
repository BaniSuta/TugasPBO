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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import util.helpers;

/**
 * FXML Controller class
 *
 * @author Lenovo - GK
 */
public class InterfaceUbahBeritaController extends helpers implements Initializable {

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
    @FXML Label lb1;

    PreparedStatement pst = null;
    Connection conn = koneksi.koneksi();
    Statement statement;
    ResultSet rs = null;
    private static int KB;
    private static int id_pen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        KB = MenuAdminController.kode;

        try {
            statement = conn.createStatement();
            String sql = "SELECT judul,jenis,tgl_berita,isi FROM berita WHERE kode_berita = " + KB;
            rs = statement.executeQuery(sql);

            if (rs.next()) {
                this.txtjudul.setText(rs.getString("judul"));
                this.tglberita.setValue(rs.getDate("tgl_berita").toLocalDate());
                this.txtisi.setText(rs.getString("isi"));
                if (rs.getString("jenis").equals(rd1.getText())) {
                    this.rd1.setSelected(true);
                } else if (rs.getString("jenis").equals(rd2.getText())) {
                    this.rd2.setSelected(true);
                } else if (rs.getString("jenis").equals(rd3.getText())) {
                    this.rd3.setSelected(true);
                } else if (rs.getString("jenis").equals(rd4.getText())) {
                    this.rd4.setSelected(true);
                } else if (rs.getString("jenis").equals(rd5.getText())) {
                    this.rd5.setSelected(true);
                } else if (rs.getString("jenis").equals(rd6.getText())) {
                    this.rd6.setSelected(true);
                }
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            Alert peringatan = new Alert(Alert.AlertType.ERROR);
            peringatan.setTitle("SQL Connection");
            peringatan.setHeaderText(null);
            peringatan.setContentText("" + e);
            peringatan.showAndWait();
        }
    }

    @FXML
    private void kembaliClick(ActionEvent event) {
        changePage(event, "MenuAdminDocument");
    }

    @FXML
    private void simpanClick(ActionEvent event) {
        int id_pen = InterfaceLoginController.id_penulis;
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
                String query = "UPDATE berita SET id_penulis = ?, judul = ?, jenis = ?, tgl_berita = ?, isi = ? WHERE kode_berita = ?";
                pst = conn.prepareStatement(query);
                pst.setInt(1, id_pen);
                pst.setString(2, judul);
                pst.setString(3, selJenis);
                pst.setString(4, tanggal);
                pst.setString(5, isi);
                pst.setInt(6, KB);

                int rowAffected = pst.executeUpdate();

                if (rowAffected > 0) {
                    Alert pemberitahuan = new Alert(Alert.AlertType.INFORMATION);
                    pemberitahuan.setTitle("Ubah Data Berhasil");
                    pemberitahuan.setHeaderText(null);
                    pemberitahuan.setContentText("Berhasil Mengubah Berita!");
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
                    pemberitahuan.setTitle("Ubah Data Gagal");
                    pemberitahuan.setHeaderText(null);
                    pemberitahuan.setContentText("Gagal Mengubah Berita!");
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
    
    public static void receiveData(int data) {
        KB = data;
    }
}
