package com.example.logingoogle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> {

    private List<E_TimeFut> listaTimes;

    public adapter(List<E_TimeFut> lista){
        this.listaTimes = lista;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomeTime;
        TextView pais;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTime = itemView.findViewById(R.id.textTime);
            pais = itemView.findViewById(R.id.textPais);
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        E_TimeFut time = listaTimes.get(position);

        holder.nomeTime.setText(time.getNomeTime());
        holder.pais.setText(time.getPais());

    }

    @Override
    public int getItemCount() {
        return listaTimes.size();
    }
}
