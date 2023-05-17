package com.example.ilongrunningtasks;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Knjiga.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract KnjigaDAO valuteDao();
}
