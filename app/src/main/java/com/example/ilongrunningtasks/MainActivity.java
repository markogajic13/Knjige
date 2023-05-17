package com.example.ilongrunningtasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView bookName;
    private TextView authorName;
    private SearchView search;
    public List<Knjiga> lista = new ArrayList<Knjiga>();
    public Knjiga result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookName= (TextView) findViewById(R.id.textView4);
        authorName= (TextView) findViewById(R.id.textView5);
        search= (SearchView) findViewById(R.id.searchView);

        bookName.setText("");
        authorName.setText("");

        AppDatabase db= Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class,
                "tecajnica")
                .allowMainThreadQueries()
                .build();

        DataSingleton.getInstance().setDb(db);

        final Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AppDatabase db=DataSingleton.getInstance().getDb();
                List<Knjiga> lista = new ArrayList<Knjiga>();
                lista.add(result);
                Knjiga[] kl = new Knjiga[lista.size()];
                kl = lista.toArray(kl);
                db.valuteDao().dodajKnjigu(kl);
            }
        });

        final Button actionButton=findViewById(R.id.favourites);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, FavouriteActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });



        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("QUERY", query);
                String adresa="https://www.goodreads.com/search?utf8=%E2%9C%93&query=" + query;
                AsyncTask<String,Integer, List<Knjiga>> download = new AsyncTask<String, Integer, List<Knjiga>>() {
                    @Override
                    protected List<Knjiga> doInBackground(String... adrese) {
                        Document doc = null;
                        try {
                            doc = Jsoup.connect(adrese[0]).get();
                            Elements redci=doc.select("tbody tr");

                            for (int i=0;i<redci.size();i++){
                                Element redak=redci.get(i);
                                Knjiga k=new Knjiga();
                                k.setNaziv(redak.selectFirst("tr td a").attr("title").toString());
                                k.setPisac(redak.selectFirst("tr td span div a span").text());
                                k.setSlika(redak.selectFirst("tr td a img").attr("src").toString());
                                lista.add(k);
                            }

                            return lista;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(List<Knjiga> knjigas) {
                        result = lista.get(0);
                        bookName.setText(result.getNaziv());
                        authorName.setText(result.getPisac());
                        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                                .execute(result.getSlika());
                        lista.clear();

                    }
                };
                download.execute(adresa);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
