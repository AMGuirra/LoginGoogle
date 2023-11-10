package com.example.logingoogle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText edEmail, edSenha;
    Button btCriar, btAcessar, btResetarSenha;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edEmail = findViewById(R.id.editTextEmail);
        edSenha = findViewById(R.id.editTextPassword);
        btCriar = findViewById(R.id.criar);
        btAcessar = findViewById(R.id.acessar);
        btResetarSenha = findViewById(R.id.resetar);
        mAuth = FirebaseAuth.getInstance();

        btCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString();
                String senha = edSenha.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Usuário criado", Toast.LENGTH_SHORT).show();
                            FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
                            if(usuario.isEmailVerified()){
                                Toast.makeText(MainActivity.this, "Usuário verificado.", Toast.LENGTH_SHORT).show();
                                usuario.sendEmailVerification();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Usuário não verificado.", Toast.LENGTH_SHORT).show();
                                usuario.sendEmailVerification();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Usuário não criado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString();
                String senha = edSenha.getText().toString();

                mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Usuário logado", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                            intent.putExtra("email_digitado", email); // Passando o email para a próxima activity
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Usuário não logado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        btResetarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

    }
}
