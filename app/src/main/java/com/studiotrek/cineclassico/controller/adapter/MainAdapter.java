package com.studiotrek.cineclassico.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studiotrek.cineclassico.R;
import com.studiotrek.cineclassico.controller.holder.MainViewHolder;
import com.studiotrek.cineclassico.model.ListaFilmes;

import java.util.List;

/**
 * Created by Admin on 06/03/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private Context context;
    private List<ListaFilmes> listaFilmesList;

    public MainAdapter(Context context, List<ListaFilmes> listaFilmesList) {
        this.context = context;
        this.listaFilmesList = listaFilmesList;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_main, parent, false);
        return new MainViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        ListaFilmes listaFilmes = listaFilmesList.get(position);
        holder.setElement(listaFilmes);
    }

    @Override
    public int getItemCount() {
        return listaFilmesList.size();
    }
}
