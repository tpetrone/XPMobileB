package br.senai.sp.informatica.listadeamigos.model;

import java.util.Date;

/**
 * Created by pena on 26/01/17.
 */

public class Amigo {
    private Long id;
    private String nome;
    private String email;
    private Date nascimento;
    private byte[] foto;

    public Amigo() {
    }

    public Amigo(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public Amigo(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
