package com.example.logingoogle;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class InicioUsuario extends AppCompatActivity {

    TextView textViewFav;
    SearchView searchView;
    RecyclerView recyclerView;
    ArrayList<Digimon> digimonArrayList = new ArrayList<>();

    Handler handler = new Handler();

    MinhaAdapter minhaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paguser);

        recyclerView = findViewById(R.id.recyclerView);
        //textViewFav = findViewById(R.id.textViewFav);
        //searchView = findViewById(R.id.searchView1);

        //recyclerView, textViewFav, e searchView são elementos fictícios, você deve substituí-los
        //por elementos reais correspondentes ao seu layout activity_paguser

        textViewFav.setVisibility(View.GONE); // Esconde o textViewFav, se não for necessário

        minhaAdapter = new MinhaAdapter(digimonArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(minhaAdapter);

        try {
            // Executa o método para carregar os Digimons
            carregarDigimons();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Pode adicionar lógica para pesquisar Digimons aqui se necessário
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Pode adicionar lógica para pesquisa em tempo real aqui se necessário
                return false;
            }
        });
    }

    private void carregarDigimons() throws ExecutionException, InterruptedException {
        // Cria uma instância do DownloadDados para buscar os Digimons
        DownloadDados downloadDados = new DownloadDados();

        // Executa a AsyncTask para baixar os Digimons da API
        // A URL aqui deve corresponder à sua API de Digimon
        ArrayList<Digimon> digimons = downloadDados.execute("https://digimon-api.vercel.app/api/digimon").get();

        if (digimons != null) {
            digimonArrayList.addAll(digimons);
            minhaAdapter.notifyDataSetChanged();
        }
    }
}
