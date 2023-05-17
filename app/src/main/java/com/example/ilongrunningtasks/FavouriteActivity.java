package com.example.ilongrunningtasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private KnjigaAdapter knjigaAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        AppDatabase db=Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class,
                "tecajnica")
                .allowMainThreadQueries()
                .build();

        DataSingleton.getInstance().setDb(db);
        
        recyclerView=findViewById(R.id.recyclerValute);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        knjigaAdapter=new KnjigaAdapter(this);
        DataSingleton.getInstance().setValuteAdapter(knjigaAdapter);
        recyclerView.setAdapter(knjigaAdapter);

        db.valuteDao().dohvatiSveKnjige().observe(this, new Observer<List<Knjiga>>() {
            @Override
            public void onChanged(List<Knjiga> valutas) {
                knjigaAdapter.setData(valutas);
            }
        });


    }

}