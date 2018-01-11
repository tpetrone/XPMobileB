package br.senai.sp.informatica.albunsmusicais.model;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by pena on 18/11/2017.
 */

@SuppressLint("SimpleDateFormat")
public class Album implements Comparable<Album> {
    private Long id;
    private String banda;
    private String album;
    private String genero;
    private Date lancamento;
    private byte[] capa;
    private boolean del;
    private static SimpleDateFormat fmtData =
            new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");

    public Album() {
    }

    public Album(Long id) {
        this.id = id;
    }

    public Album(Long id, String banda, String nome, String genero, Date lancamento) {
        this.id = id;
        this.banda = banda;
        this.album = nome;
        this.genero = genero;
        this.lancamento = lancamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBanda() {
        return banda;
    }

    public void setBanda(String banda) {
        this.banda = banda;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getLancamento() {
        return lancamento;
    }

    public String getDataDeLancamento() {
        return fmtData.format(lancamento);
    }

    public void setLancamento(Date lancamento) {
        this.lancamento = lancamento;
    }

    public byte[] getCapa() {
        return capa;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        if (!id.equals(album.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", banda='" + banda + '\'' +
                ", album='" + album + '\'' +
                ", genero='" + genero + '\'' +
                ", lancamento=" + lancamento +
                '}';
    }

    @Override
    public int compareTo(@NonNull Album outra) {
        return banda.compareToIgnoreCase(outra.banda);
    }
}
