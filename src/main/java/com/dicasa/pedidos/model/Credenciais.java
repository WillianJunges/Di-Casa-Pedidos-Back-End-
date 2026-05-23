package com.dicasa.pedidos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Credenciais {

    @Column(name = "cred_usuario")
    private String usuario;

    @Column(name = "cred_senha")
    private String senha;

    public Credenciais() {}

    public Credenciais(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
