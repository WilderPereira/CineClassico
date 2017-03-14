package com.studiotrek.cineclassico.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 13/03/2017.
 */

public class ListaFilmes implements Parcelable {

    private String genero;
    private List<Filme> filmes;

    public ListaFilmes() {

    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
    }

    protected ListaFilmes(Parcel in) {
        genero = in.readString();
        if (in.readByte() == 0x01) {
            filmes = new ArrayList<Filme>();
            in.readList(filmes, Filme.class.getClassLoader());
        } else {
            filmes = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(genero);
        if (filmes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(filmes);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ListaFilmes> CREATOR = new Parcelable.Creator<ListaFilmes>() {
        @Override
        public ListaFilmes createFromParcel(Parcel in) {
            return new ListaFilmes(in);
        }

        @Override
        public ListaFilmes[] newArray(int size) {
            return new ListaFilmes[size];
        }
    };
}
