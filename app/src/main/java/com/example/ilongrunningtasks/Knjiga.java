package com.example.ilongrunningtasks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Knjiga {
    @PrimaryKey (autoGenerate = true)
    private int id;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    private String naziv;
    private String pisac;
    private String slika;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPisac() {
        return pisac;
    }

    public void setPisac(String pisac) {
        this.pisac = pisac;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

}