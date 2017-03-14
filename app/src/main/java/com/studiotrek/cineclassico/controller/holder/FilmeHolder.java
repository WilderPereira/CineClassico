package com.studiotrek.cineclassico.controller.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.studiotrek.cineclassico.R;
import com.studiotrek.cineclassico.model.Filme;

import java.util.List;

/**
 * Created by Admin on 13/03/2017.
 */

public class FilmeHolder extends RecyclerView.ViewHolder {

    private Context context;
    private ImageView ivCardImagem1;

    public FilmeHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;

        ivCardImagem1 = (ImageView) itemView.findViewById(R.id.iv_card_imagem1);

        ivCardImagem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setElements(Filme filme) {
        Picasso.with(context)
                .load(filme.getImagem())
                .resize(120, 150)
                .centerCrop()
                .into(ivCardImagem1);

        /*Picasso.with(context)
                .load(filmes.get(1).getImagem())
                .resize(120, 150)
                .centerCrop()
                .into(ivCardImagem2);

        Picasso.with(context)
                .load(filmes.get(2).getImagem())
                .resize(120, 150)
                .centerCrop()
                .into(ivCardImagem3);*/
    }

}
