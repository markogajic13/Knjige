package com.example.ilongrunningtasks;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KnjigaAdapter extends RecyclerView.Adapter<KnjigaAdapter.ViewHolder> {

    List<Knjiga> data=null;
    AppCompatActivity activity;

    public KnjigaAdapter(AppCompatActivity activity){
        this.activity=activity;
    }

    public void setData(List<Knjiga> data){
        this.data=data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.redak_knjiga, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Knjiga v=data.get(position);

        holder.tOznaka.setText(v.getNaziv());
        holder.tParitet.setText(v.getPisac().toString());

        holder.layout.setBackgroundColor(position%2==0? Color.CYAN:Color.YELLOW);

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DataSingleton.getInstance().getDb().valuteDao().obrisiKnjigu(v);
                return true;
            }
        });

    }
    public void clearCurrent(){trenutnaOdabranaPozicija=null;}
    Integer trenutnaOdabranaPozicija=null;
    public Knjiga getCurrentKnjiga(){
        if (trenutnaOdabranaPozicija==null) return null;
        return data.get(trenutnaOdabranaPozicija.intValue());
    }

    @Override
    public int getItemCount() {
        if (data==null) return 0;
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tOznaka;
        public TextView tParitet;

        public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tOznaka=itemView.findViewById(R.id.tOznaka);
            tParitet=itemView.findViewById(R.id.tParitet);

            layout=itemView;
        }
    }
}
