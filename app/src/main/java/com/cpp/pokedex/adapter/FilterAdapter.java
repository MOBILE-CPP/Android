package com.cpp.pokedex.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cpp.pokedex.R;
import com.cpp.pokedex.models.Nomes;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {
    private Nomes lista;
    TextView[] t;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_cell,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        for(int i=0; i<getItemCount();i++){
            String[] nomes = lista.getNomes();
            holder.nome.setText(nomes[i]);
        }
    }

    @Override
    public int getItemCount() {
        return this.lista.getNomes().length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome;


        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            nome = itemView.findViewById(R.id.textViewCellNomeP);

        }

    }

    public FilterAdapter(Nomes lista){
        this.lista = lista;
    }

}
