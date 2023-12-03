package com.example.logingoogle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class D_PagUser extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<E_TimeFut> listaTimes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_paguser);

        recyclerView = findViewById(R.id.recyclerView);

        this.criarTimes();

        adapter adapter = new adapter(listaTimes);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    public void criarTimes(){

        E_TimeFut time1 = new E_TimeFut("MANCity", "ING");
        listaTimes.add(time1);

        E_TimeFut time2 = new E_TimeFut("BARCA", "Espanha");
        listaTimes.add(time2);

        E_TimeFut time3 = new E_TimeFut("PAL", "BR");
        listaTimes.add(time3);

    }
}
