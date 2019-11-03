package com.example.latihanlayout;

public class DataMatakuliah {
    private String dosen;
    private String namaMk;
    private int sks;


    public DataMatakuliah(String namaMk, int sks, String dosen) {
        this.dosen = dosen;
        this.namaMk = namaMk;
        this.sks = sks;
    }

    public String getNamaMk() {
        return namaMk;
    }

    public void setNamaMk(String namaMk) {
        this.namaMk = namaMk;
    }

    public int getSks() {
        return sks;
    }

    public void setSks(int sks) {
        this.sks = sks;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }
}
