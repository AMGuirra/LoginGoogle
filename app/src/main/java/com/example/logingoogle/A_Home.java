package com.example.logingoogle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class A_Home extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonCreate, buttonLogin, buttonReset;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_home);

        // Inicialização dos elementos da UI
        initializeViews();

        // Inicialização da instância do FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Configuração dos cliques dos botões
        buttonCreate.setOnClickListener(view -> {
            Intent intent = new Intent(A_Home.this, B_CriarUser.class);
            startActivity(intent);
        });

        buttonLogin.setOnClickListener(view -> loginUser());

        buttonReset.setOnClickListener(view -> {
            Intent intent = new Intent(A_Home.this, C_ResetarUser.class);
            startActivity(intent);
        });
    }

    // Método para inicializar os elementos da UI
    private void initializeViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonCreate = findViewById(R.id.buttonCreate);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonReset = findViewById(R.id.buttonReset);
    }

    // Método para fazer login do usuário
    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            showToast("Por favor, insira um e-mail");
        } else if (!isValidEmail(email)) {
            showToast("Email inválido");
        } else {
            firebaseLogin(email, password);
        }
    }

    // Método para exibir um Toast com uma mensagem
    private void showToast(String message) {
        Toast.makeText(A_Home.this, message, Toast.LENGTH_SHORT).show();
    }

    // Método para verificar se o e-mail possui um formato válido
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Método para autenticar o usuário no Firebase
    private void firebaseLogin(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Se a autenticação for bem-sucedida, inicia a próxima atividade
                        Toast.makeText(A_Home.this, "Usuário logado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(A_Home.this, D_PagUser.class);
                        intent.putExtra("email_digitado", email);
                        startActivity(intent);
                    } else {
                        // Se houver erro na autenticação, exibe uma mensagem de erro
                        Toast.makeText(A_Home.this, "Erro ao fazer login", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
