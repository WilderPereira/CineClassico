package com.studiotrek.cineclassico.controller.holder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.studiotrek.cineclassico.R;
import com.studiotrek.cineclassico.controller.adapter.FilmeAdapter;
import com.studiotrek.cineclassico.model.ListaFilmes;

/**
 * Created by Admin on 04/03/2017.
 */

public class MainViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView tvCellGenero;
    private RecyclerView rvCellLista;

    public MainViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        tvCellGenero = (TextView) itemView.findViewById(R.id.tv_cell_genero);
        rvCellLista = (RecyclerView) itemView.findViewById(R.id.rv_cell_lista);
    }

    public void setElement(ListaFilmes listaFilmes) {
        tvCellGenero.setText(listaFilmes.getGenero());

        rvCellLista.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvCellLista.setLayoutManager(mLayoutManager);
        FilmeAdapter filmeAdapter = new FilmeAdapter(context, listaFilmes.getFilmes());
        rvCellLista.setAdapter(filmeAdapter);
    }
}
