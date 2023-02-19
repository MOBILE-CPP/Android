package com.cpp.pokedex.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cpp.pokedex.R;
import com.cpp.pokedex.models.PokemonModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PokeAdapter extends RecyclerView.Adapter<PokeAdapter.MyViewHolder> {
    private List<PokemonModel> lista;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.poke_cell,parent,false);
        return new MyViewHolder(item);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PokemonModel pokemon = lista.get(position);
        holder.nome.setText(pokemon.getName());
        holder.tipo.setText(pokemon.getType());
        List<String> habil = pokemon.getSkills();
        if (habil.size()==1) {
            holder.hab01.setText(habil.get(0));
        } else if (habil.size()==2) {
            holder.hab01.setText(habil.get(0));
            holder.hab02.setText(habil.get(1));
        } else if (habil.size()==3) {
            holder.hab01.setText(habil.get(0));
            holder.hab02.setText(habil.get(1));
            holder.hab03.setText(habil.get(2));
        }
        Picasso.get().load("http://10.0.2.2:8480/image/"+pokemon.getImageData()+"").placeholder(R.drawable.pokeball).into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome;
        TextView tipo;
        TextView hab01;
        TextView hab02;
        TextView hab03;
        TextView hab04;
        ImageView imagen;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            nome = itemView.findViewById(R.id.textViewCellNomeP);
            tipo = itemView.findViewById(R.id.textViewCellTipoP);
            hab01 = itemView.findViewById(R.id.textViewHab01);
            hab02 = itemView.findViewById(R.id.textViewHab02);
            hab03 = itemView.findViewById(R.id.textViewHab03);
            hab04 = itemView.findViewById(R.id.textViewHab04);
            imagen = itemView.findViewById(R.id.imageView);

        }

    }

    public PokeAdapter(List<PokemonModel> lista){
        this.lista = lista;
    }

}
