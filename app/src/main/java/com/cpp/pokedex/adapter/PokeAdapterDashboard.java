package com.cpp.pokedex.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cpp.pokedex.R;
import com.cpp.pokedex.models.PokemonModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokeAdapterDashboard extends RecyclerView.Adapter<PokeAdapterDashboard.MyViewHolder> {

    private List<PokemonModel> lista;

    @NonNull
    @Override
    public PokeAdapterDashboard.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.tipo_cell,parent,false);
        return new PokeAdapterDashboard.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull PokeAdapterDashboard.MyViewHolder holder, int position) {
        PokemonModel pokemon = lista.get(position);
        holder.nome.setText(pokemon.getName());
        holder.tipo.setText(pokemon.getType());
        List<String> habil = pokemon.getSkills();
        List<TextView> habilitys = Arrays.asList(new TextView[]{holder.hab01, holder.hab02, holder.hab03});
        for(int i=0;i<habil.size();i++)
            habilitys.get(i).setText(habil.get(i));
        holder.usuario.setText(pokemon.getUsername());
        String url = "http://10.0.2.2:5010/image/"+pokemon.getImageData();
        Picasso.get().load(url).into(holder.imagen);
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
//        TextView hab04;
        TextView usuario;
        ImageView imagen;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            nome = itemView.findViewById(R.id.textViewTopNomePok);
            tipo = itemView.findViewById(R.id.textViewTitleTop);
            hab01 = itemView.findViewById(R.id.textViewHb01Top);
            hab02 = itemView.findViewById(R.id.textViewHb02Top);
            hab03 = itemView.findViewById(R.id.textViewHb03Top);
//            hab04 = itemView.findViewById(R.id.textViewHb04Top);
            imagen = itemView.findViewById(R.id.imageViewTopCell);

        }

    }

    public PokeAdapterDashboard(List<PokemonModel> lista){
        this.lista = lista;
    }
}
