package br.senai.sp.informatica.exemploautenticacao.model;

/**
 * Created by pena on 11/02/2018.
 */

public class Usuario {
    private String id;
    private String email;
    private boolean logado;

    public Usuario() {
    }

    public Usuario(String id, String email) {
        this.id = id;
        this.email = email;
        this.logado = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }
}
