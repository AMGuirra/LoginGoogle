package com.example.logingoogle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity3 extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Button btnVoltar, btnSalvar;
    EditText editTextNome; // Adicionando referência ao EditText

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        TextView textEmail = findViewById(R.id.textEmail);
        editTextNome = findViewById(R.id.digite); // Referenciando o EditText

        Intent intent = getIntent();
        if (intent != null) {
            String email = intent.getStringExtra("email_digitado");
            textEmail.setText("Olá: " + email);
        }

        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnSalvar = findViewById(R.id.btnSalvar); // Inicializa o botão Salvar
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aqui você obtém o texto digitado no EditText
                String nome = editTextNome.getText().toString();

                // Verifique se o nome não está vazio antes de salvar
                if (!nome.isEmpty()) {
                    // Obtém a referência para o nó 'usuarios' no Firebase Database
                    DatabaseReference usuariosRef = databaseReference.child("usuarios");

                    // Obtém o último ID utilizado
                    usuariosRef.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int novoId = 1; // Define o primeiro ID como 1 por padrão

                            // Verifica se há algum usuário no banco de dados
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    // Obtém o último ID e incrementa
                                    Pessoa lastUser = snapshot.getValue(Pessoa.class);
                                    novoId = Integer.parseInt(lastUser.getId()) + 1;
                                }
                            }

                            // Cria um objeto para armazenar os dados do novo usuário
                            String novoIdString = String.valueOf(novoId);
                            Pessoa usuario = new Pessoa(novoIdString, nome);

                            // Salve o usuário no Firebase Database com o ID sequencial
                            usuariosRef.child(novoIdString).setValue(usuario);

                            // Mostra um Toast informando que os dados foram salvos
                            Toast.makeText(MainActivity3.this, "Salvo no banco", Toast.LENGTH_SHORT).show();

                            // Limpa o campo 'digite' para o próximo preenchimento
                            editTextNome.getText().clear();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Tratamento de erro, se necessário
                        }
                    });
                } else {
                    // Caso o campo esteja vazio, exibe um Toast pedindo para preencher
                    Toast.makeText(MainActivity3.this, "Preencha o campo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
