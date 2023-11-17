package com.example.logingoogle;

public class Pessoa {
    String id;
    String nome;

    public Pessoa() {
        // Construtor vazio necessário para Firebase
    }

    public Pessoa(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
