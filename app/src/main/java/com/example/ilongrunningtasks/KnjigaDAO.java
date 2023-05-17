package com.example.ilongrunningtasks;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface KnjigaDAO {
    @Query("SELECT * FROM Knjiga")
    LiveData<List<Knjiga>> dohvatiSveKnjige();

    @Query("SELECT * FROM Knjiga WHERE id=:valutaId ")
    List<Knjiga> dohvatiJednuKnjigu(int valutaId);

    @Query("DELETE FROM Knjiga")
    void obrisiSveKnjige();

    @Update
    public void izmjeniKnjigu(Knjiga... valute);

    @Insert
    public void dodajKnjigu(Knjiga... valute);

    @Delete
    public void obrisiKnjigu(Knjiga... valute);
}
