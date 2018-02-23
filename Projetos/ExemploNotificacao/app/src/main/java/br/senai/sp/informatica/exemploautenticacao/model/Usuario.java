package br.senai.sp.informatica.exemploautenticacao.model;


import java.util.Map;

public class Usuario {
    private String id;
    private String email;
    private String token;
    private boolean logado;

    public Usuario() {
    }

    public Usuario(String id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }
}
