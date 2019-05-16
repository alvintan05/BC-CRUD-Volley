package com.alvin.crud_volley.model;

public class ModelData {
    String nim, nama, kelas, prodi;

    public ModelData() {
    }

    public ModelData(String nim, String nama, String kelas, String prodi) {
        this.nim = nim;
        this.nama = nama;
        this.kelas = kelas;
        this.prodi = prodi;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }
}
