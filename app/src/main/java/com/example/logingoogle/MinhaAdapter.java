package com.example.logingoogle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MinhaAdapter extends RecyclerView.Adapter<MinhaAdapter.MyViewHolder> {

    private List<Digimon> listaDigimons;

    public MinhaAdapter(List<Digimon> lista) {
        this.listaDigimons = lista;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomeDigimon;
        TextView nivelDigimon;
        ImageView imagemDigimon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeDigimon = itemView.findViewById(R.id.textNome); // Substituir pelo ID correto do TextView de nome
            nivelDigimon = itemView.findViewById(R.id.textNivel); // Substituir pelo ID correto do TextView de nível
            imagemDigimon = itemView.findViewById(R.id.imageView); // Substituir pelo ID correto do ImageView
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_digimon, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Digimon digimon = listaDigimons.get(position);

        holder.nomeDigimon.setText(digimon.getName());
        holder.nivelDigimon.setText(digimon.getLevel());

        // Carregar a imagem usando Picasso ou outra biblioteca similar
        Picasso.get().load(digimon.getImg()).into(holder.imagemDigimon);
    }

    @Override
    public int getItemCount() {
        return listaDigimons.size();
    }
}
