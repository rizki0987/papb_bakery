package com.example.myapplication;

public class pesanan {
    private int id_pesanan;
    private String kode_pesanan;
    private int id_user;
    private int total;
    private int is_paid;
    private int[] menu;
    private int id_menu;
    private String nama;
    private int id_jenis;
    private int stok;
    private int harga;
    private String deskripsi;
    private String kategori;

    public int getId_pesanan() {
        return id_pesanan;
    }

    public void setId_pesanan(int id_pesanan) {
        this.id_pesanan = id_pesanan;
    }

    public String getKode_pesanan() {
        return kode_pesanan;
    }

    public void setKode_pesanan(String kode_pesanan) {
        this.kode_pesanan = kode_pesanan;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(int is_paid) {
        this.is_paid = is_paid;
    }

    public int[] getMenu() {
        return menu;
    }

    public void setMenu(int[] menu) {
        this.menu = menu;
    }

    public int getId_menu() {
        return id_menu;
    }

    public String getNama() {
        return nama;
    }

    public int getId_jenis() {
        return id_jenis;
    }

    public int getStok() {
        return stok;
    }

    public int getHarga() {
        return harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getKategori() {
        return kategori;
    }
}
