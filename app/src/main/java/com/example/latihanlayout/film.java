package com.example.latihanlayout;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class film extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> listFilm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        listFilm = new ArrayList<>();
        setData();

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter(listFilm);
        recyclerView.setAdapter(adapter);

    }

    private void setData(){
        listFilm.add("film satu");
        listFilm.add("film dua");
        listFilm.add("film tiga");
        listFilm.add("film empat");
        listFilm.add("film lima");
        listFilm.add("film enam");
        listFilm.add("film tujuh");
    }
}