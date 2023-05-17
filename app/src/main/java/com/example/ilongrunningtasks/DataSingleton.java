package com.example.ilongrunningtasks;

public class DataSingleton {
    private static DataSingleton instance=null;
    public static DataSingleton getInstance(){
        if (instance==null) instance=new DataSingleton();
        return instance;
    }

    private KnjigaAdapter knjigaAdapter=null;

    public KnjigaAdapter getKnjigaAdapter() {
        return knjigaAdapter;
    }

    public void setValuteAdapter(KnjigaAdapter knjigaAdapter) {
        this.knjigaAdapter = knjigaAdapter;
    }

    AppDatabase db=null;

    public AppDatabase getDb() {
        return db;
    }

    public void setDb(AppDatabase db) {
        this.db = db;
    }
}
