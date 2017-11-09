package br.senai.sp.informatica.listadejogos.model;

import android.support.annotation.NonNull;

/**
 * Created by pena on 31/10/2017.
 */


public class Jogo implements Comparable<Jogo> {
    private Long id;
    private String nome;
    private String genero;
    // Atributo para identificar que o Jogo esta marcado para exclusão
    private boolean del;

    public Jogo() {
    }

    // Implementado para o uso do método indexOf na classe JogoDao
    public Jogo(Long id) {
        this.id = id;
    }

    public Jogo(Long id, String nome, String genero) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    // Implementado para o uso do método indexOf na classe JogoDao
    // estes métodos são necessários para que as pesquisas e teste
    // de igualdade funcionem adequadamente quando esta classe for
    // utilizada em Collections tais como List, Set e Map
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jogo)) return false;

        Jogo jogo = (Jogo) o;

        if (!id.equals(jogo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(@NonNull Jogo outro) {
        return nome.compareTo(outro.nome);
    }
}
