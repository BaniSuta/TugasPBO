/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wahidiyanpost;

/**
 *
 * @author Lenovo - GK
 */
public class Berita {
    private int kodeBerita;
    protected String judul;
    protected String penulis;
    protected String isi;
    protected String tgl_berita;
    protected String jenis;

    public Berita(int kodeBerita, String judul, String penulis, String isi, String tgl_berita, String jenis) {
        this.kodeBerita = kodeBerita;
        this.judul = judul;
        this.penulis = penulis;
        this.isi = isi;
        this.tgl_berita = tgl_berita;
        this.jenis = jenis;
    }

    public int getKodeBerita() {
        return kodeBerita;
    }

    public void setKodeBerita(int kodeBerita) {
        this.kodeBerita = kodeBerita;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getTgl_berita() {
        return tgl_berita;
    }

    public void setTgl_berita(String tgl_berita) {
        this.tgl_berita = tgl_berita;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
