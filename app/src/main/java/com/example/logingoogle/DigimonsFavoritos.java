package com.example.n2tsi;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logingoogle.Digimon;
import com.example.logingoogle.MinhaAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DigimonsFavoritos extends AppCompatActivity {

    TextView textViewLogout;
    SearchView searchView;
    RecyclerView recyclerView;
    ArrayList<Digimon> digimonArrayListFavoritos = new ArrayList<>();

    MinhaAdapter minhaAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_favoritos);

        recyclerView = findViewById(R.id.recyclerViewFav);
        textViewLogout = findViewById(R.id.textViewLogout);
        searchView = findViewById(R.id.searchViewFav);

        //searchView aberto
        searchView.setIconified(false);
        //retira o foco automático e fecha o teclado ao iniciar a aplicação
        searchView.clearFocus();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //botão para efetuar logout
        textViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DigimonsFavoritos.this, MainActivity.class));
            }
        });

        //método para consultar o Firebase e popular o arraylist com os dados
        setInfo();

        //novamente, após a primeira exibição (acima), o usuário pode
        //fazer uma consulta de um termo entre os Digimons favoritos salvos
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                //método filtrar() definido na classe MinhaAdapter,
                //parâmetro = o que foi digitado na busca
                minhaAdapter.filtrar(s);
                //notifica o adapter para alterações na lista
                minhaAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //igual acima, para digitação sem submit
                minhaAdapter.filtrar(s);
                //notifica o adapter para alterações na lista
                minhaAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void setInfo() {
        Query query;

        //usuário logado no momento
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //limpando o array para a consulta
        digimonArrayListFavoritos.clear();

        //o caminho da query no Firebase (todos os Digimons)
        query = databaseReference.child(user.getUid()).child("Digimons");

        //execução da query. Caso haja dados, cai no método onDataChange
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot objDataSnapshot1 : dataSnapshot.getChildren()) {
                        Digimon digimon = objDataSnapshot1.getValue(Digimon.class);
                        digimonArrayListFavoritos.add(digimon);
                    }
                    //setRecyclerView() para montagem e configuração da RecyclerView mas
                    //neste caso, setRecyclerView() tem que ser chamado aqui (dentro e ao final de onDataChange),
                    //de forma que é executado somente após os dados acima serem baixados do Firebase
                    setRecyclerView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setRecyclerView() {
        minhaAdapter = new MinhaAdapter(digimonArrayListFavoritos);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(minhaAdapter);
    }
}
