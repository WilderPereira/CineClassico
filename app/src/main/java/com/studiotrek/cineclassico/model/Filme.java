package com.studiotrek.cineclassico.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 04/03/2017.
 */

public class Filme implements Parcelable {

    private Integer codigo;
    private String nome;
    private String imagem;
    private String urlDublado;
    private String urlLegendado;
    private String ano;
    private float classificacao;
    private String duracao;
    private String genero;
    private String sinopse;
    private String ativo;
    private String data;

    public Filme() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getUrlDublado() {
        return urlDublado;
    }

    public void setUrlDublado(String urlDublado) {
        this.urlDublado = urlDublado;
    }

    public String getUrlLegendado() {
        return urlLegendado;
    }

    public void setUrlLegendado(String urlLegendado) {
        this.urlLegendado = urlLegendado;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public float getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(float classificacao) {
        this.classificacao = classificacao;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Filme(Parcel in) {
        codigo = in.readByte() == 0x00 ? null : in.readInt();
        nome = in.readString();
        imagem = in.readString();
        urlDublado = in.readString();
        urlLegendado = in.readString();
        ano = in.readString();
        classificacao = in.readFloat();
        duracao = in.readString();
        genero = in.readString();
        sinopse = in.readString();
        ativo = in.readString();
        data = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (codigo == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(codigo);
        }
        dest.writeString(nome);
        dest.writeString(imagem);
        dest.writeString(urlDublado);
        dest.writeString(urlLegendado);
        dest.writeString(ano);
        dest.writeFloat(classificacao);
        dest.writeString(duracao);
        dest.writeString(genero);
        dest.writeString(sinopse);
        dest.writeString(ativo);
        dest.writeString(data);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Filme> CREATOR = new Parcelable.Creator<Filme>() {
        @Override
        public Filme createFromParcel(Parcel in) {
            return new Filme(in);
        }

        @Override
        public Filme[] newArray(int size) {
            return new Filme[size];
        }
    };
}
