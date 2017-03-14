package com.studiotrek.cineclassico.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studiotrek.cineclassico.R;
import com.studiotrek.cineclassico.controller.holder.FilmeHolder;
import com.studiotrek.cineclassico.model.Filme;

import java.util.List;

/**
 * Created by Admin on 13/03/2017.
 */

public class FilmeAdapter extends RecyclerView.Adapter<FilmeHolder> {

    private Context context;
    private List<Filme> filmes;

    public FilmeAdapter(Context context, List<Filme> filmes) {
        this.context = context;
        this.filmes = filmes;
    }

    @Override
    public FilmeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_imagem, parent, false);
        return new FilmeHolder(context, view);
    }

    @Override
    public void onBindViewHolder(FilmeHolder holder, int position) {
        Filme filme = filmes.get(position);
        holder.setElements(filme);
    }

    @Override
    public int getItemCount() {
        return filmes.size();
    }
}
