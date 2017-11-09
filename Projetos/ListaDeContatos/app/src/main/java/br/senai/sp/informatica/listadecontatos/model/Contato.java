package br.senai.sp.informatica.listadecontatos.model;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by pena on 31/10/2017.
 */

public class Contato implements Comparable<Contato> {
    private Long id;
    private String nome;
    private String email;
    private Date nascimento;

    public Contato() {
    }

    // Implementado para o uso do método indexOf na classe ContatoDao
    public Contato(Long id) {
        this.id = id;
    }

    public Contato(Long id, String nome, String genero, Date nascimento) {
        this.id = id;
        this.nome = nome;
        this.email = genero;
        this.nascimento = nascimento;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    // Implementado para o uso do método indexOf na classe ContatoDao
    // estes métodos são necessários para que as pesquisas e teste
    // de igualdade funcionem adequadamente quando esta classe for
    // utilizada em Collections tais como List, Set e Map
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contato)) return false;

        Contato jogo = (Contato) o;

        if (!id.equals(jogo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(@NonNull Contato outro) {
        return nome.compareTo(outro.nome);
    }
}
