package com.example.logingoogle;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class B_CriarUser extends AppCompatActivity {

    EditText editTextNome, editTextEmail, editTextPassword;
    Button buttonCreate;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_criaruser);

        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonCreate = findViewById(R.id.criar);
        firebaseAuth = getFirebaseAuthInstance();

        buttonCreate.setOnClickListener(view -> {
            String nome = editTextNome.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (nome.isEmpty()) {
                Toast.makeText(B_CriarUser.this, "Por favor, insira um nome", Toast.LENGTH_SHORT).show();
            } else if (email.isEmpty()) {
                Toast.makeText(B_CriarUser.this, "Por favor, insira um e-mail", Toast.LENGTH_SHORT).show();
            } else if (isValidEmail(email)) {
                createFirebaseUser(nome, email, password);
            } else {
                Toast.makeText(B_CriarUser.this, "Email inválido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private FirebaseAuth getFirebaseAuthInstance() {
        return FirebaseAuth.getInstance();
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void createFirebaseUser(String nome, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(B_CriarUser.this, "Usuário criado", Toast.LENGTH_SHORT).show();
                        // Limpar os campos após criar o usuário com sucesso
                        editTextNome.setText("");
                        editTextEmail.setText("");
                        editTextPassword.setText("");
                    } else {
                        Toast.makeText(B_CriarUser.this, "Erro ao criar usuário", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
