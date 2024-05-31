/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package wahidiyanpost;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import util.helpers;

/**
 *
 * @author Lenovo - GK
 */
public class MenuAdminController extends helpers implements Initializable {

    @FXML
    private TableView<Berita> tb1;
    @FXML
    private TableColumn<Berita, Integer> kolom_kode;
    @FXML
    private TableColumn<Berita, String> kolom_penulis;
    @FXML
    private TableColumn<Berita, String> kolom_judul;
    @FXML
    private TableColumn<Berita, String> kolom_jenis;
    @FXML
    private TableColumn<Berita, String> kolom_tgl;
    @FXML
    private TableColumn<Berita, String> kolom_isi;
    @FXML
    private Button btntambah;
    @FXML
    private Button btnubah;
    @FXML
    private Button btnhapus;
    @FXML
    private Button btnlogout;
    @FXML
    private TextField txtsearch;

    PreparedStatement pst = null;
    Connection conn = koneksi.koneksi();
    Statement statement;
    ResultSet rs = null;
    private Berita brt;
    public static ObservableList<Berita> listBerita;

    public static int kode;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadData();
        
        if(InterfaceLoginController.isPenulis == false){
            btntambah.setVisible(false);
            btnubah.setVisible(false);
            btnhapus.setVisible(false);
        }
    }

    private void loadData() {
        try {
            statement = conn.createStatement();
            String sql = "SELECT kode_berita,username,judul,jenis,tgl_berita,isi FROM berita JOIN akun ON berita.id_penulis = akun.id_akun";

            rs = statement.executeQuery(sql);

            this.listBerita = FXCollections.observableArrayList();

            while (rs.next()) {
                this.listBerita.add(new Berita(
                        rs.getInt("kode_berita"),
                        rs.getString("judul"),
                        rs.getString("username"),
                        rs.getString("isi"),
                        rs.getDate("tgl_berita").toString(),
                        rs.getString("jenis")
                ));
            }
            this.kolom_kode.setCellValueFactory(new PropertyValueFactory<Berita, Integer>("kodeBerita"));
            this.kolom_penulis.setCellValueFactory(new PropertyValueFactory<Berita, String>("penulis"));
            this.kolom_judul.setCellValueFactory(new PropertyValueFactory<Berita, String>("judul"));
            this.kolom_jenis.setCellValueFactory(new PropertyValueFactory<Berita, String>("jenis"));
            this.kolom_tgl.setCellValueFactory(new PropertyValueFactory<Berita, String>("tgl_berita"));
            this.kolom_isi.setCellValueFactory(new PropertyValueFactory<Berita, String>("isi"));

            this.tb1.setItems(this.listBerita);

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
    
    private void loadData(String search) {
        try {
            statement = conn.createStatement();
            String sql = "SELECT kode_berita,username,judul,jenis,tgl_berita,isi FROM berita JOIN akun ON berita.id_penulis = akun.id_akun WHERE akun.username LIKE '%" + search + "%' OR berita.judul LIKE '%" + search + "%' OR berita.jenis LIKE '%" + search + "%' OR berita.isi LIKE '%" + search + "%'";
            rs = statement.executeQuery(sql);

            this.listBerita = FXCollections.observableArrayList();

            while (rs.next()) {
                this.listBerita.add(new Berita(
                        rs.getInt("kode_berita"),
                        rs.getString("judul"),
                        rs.getString("username"),
                        rs.getString("isi"),
                        rs.getDate("tgl_berita").toString(),
                        rs.getString("jenis")
                ));
            }
            this.kolom_kode.setCellValueFactory(new PropertyValueFactory<Berita, Integer>("kodeBerita"));
            this.kolom_penulis.setCellValueFactory(new PropertyValueFactory<Berita, String>("penulis"));
            this.kolom_judul.setCellValueFactory(new PropertyValueFactory<Berita, String>("judul"));
            this.kolom_jenis.setCellValueFactory(new PropertyValueFactory<Berita, String>("jenis"));
            this.kolom_tgl.setCellValueFactory(new PropertyValueFactory<Berita, String>("tgl_berita"));
            this.kolom_isi.setCellValueFactory(new PropertyValueFactory<Berita, String>("isi"));

            this.tb1.setItems(this.listBerita);

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
    private void searchRelease(KeyEvent event) {
        String cari = txtsearch.getText();
        loadData(cari);
    }

    @FXML
    private void tambahClick(ActionEvent event) {
        changePage(event, "InterfaceTambahBerita");
    }

    @FXML
    private void ubahClick(ActionEvent event) {
        changePage(event, "InterfaceUbahBerita");
    }

    @FXML
    private void tableClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            ObservableList<Berita> selectedItems = tb1.getSelectionModel().getSelectedItems();
            if (!selectedItems.isEmpty()) {
                Berita clickedTable = selectedItems.get(0);
                kode = clickedTable.getKodeBerita();
            }
        }
    }

    @FXML
    private void hapusClick(ActionEvent event) {
        Alert info = new Alert(Alert.AlertType.CONFIRMATION, "Apakah anda yakin ingin menghapus data " + kode + " ?", ButtonType.YES, ButtonType.NO);
        info.setTitle("Delete data");
        info.showAndWait();

        if (info.getResult() == ButtonType.YES) {
            try {
                String query = "DELETE FROM berita WHERE kode_berita = ?";
                pst = conn.prepareStatement(query);
                pst.setInt(1, kode);

                int rowAffected = pst.executeUpdate();

                if (rowAffected > 0) {
                    Alert pemberitahuan = new Alert(Alert.AlertType.INFORMATION);
                    pemberitahuan.setTitle("Hapus Data Berhasil");
                    pemberitahuan.setHeaderText(null);
                    pemberitahuan.setContentText("Berhasil Menghapus Berita!");
                    pemberitahuan.showAndWait();
                    pemberitahuan.setResizable(false);
                    loadData();

                } else {
                    Alert pemberitahuan = new Alert(Alert.AlertType.INFORMATION);
                    pemberitahuan.setTitle("Hapus Data Gagal");
                    pemberitahuan.setHeaderText(null);
                    pemberitahuan.setContentText("Gagal Menghapus Berita!");
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
    
    @FXML
    private void logoutClick(ActionEvent event){
        changePage(event, "InterfaceLogin");
    }
}
