// MainActivity4.java
package com.example.logingoogle;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity4 extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonCreate;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonCreate = findViewById(R.id.criar);
        firebaseAuth = getFirebaseAuthInstance();

        buttonCreate.setOnClickListener(view -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(MainActivity4.this, "Por favor, insira um e-mail", Toast.LENGTH_SHORT).show();
            } else if (isValidEmail(email)) {
                createFirebaseUser(email, password);
            } else {
                Toast.makeText(MainActivity4.this, "Email inválido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private FirebaseAuth getFirebaseAuthInstance() {
        return FirebaseAuth.getInstance();
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void createFirebaseUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity4.this, "Usuário criado", Toast.LENGTH_SHORT).show();
                        // Aqui você pode adicionar qualquer lógica adicional ou navegar para outra atividade se necessário
                    } else {
                        Toast.makeText(MainActivity4.this, "Erro ao criar usuário", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
