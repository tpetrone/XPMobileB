package br.senai.sp.informatica.meuhandler.model;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pena on 18/11/2017.
 */

@SuppressLint("SimpleDateFormat")
public class Album  {
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

    @JsonIgnore
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
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", banda='" + banda + '\'' +
                ", album='" + album + '\'' +
                ", genero='" + genero + '\'' +
                ", lancamento=" + lancamento +
                '}';
    }
}
