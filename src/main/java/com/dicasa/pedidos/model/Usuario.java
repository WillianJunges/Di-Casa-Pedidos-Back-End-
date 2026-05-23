package com.dicasa.pedidos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    private String id;

    @Column(nullable = false)
    private String nome;

    private String telefone;
    private String endereco;

    @Column(unique = true)
    private String email;

    private Double distancia = 0.0;

    @Column(name = "taxa_entrega")
    private Double taxaEntrega = 0.0;

    @Column(name = "is_admin")
    private Boolean isAdmin = false;

    @Embedded
    private Credenciais credenciais;

    @PrePersist
    public void prePersist() {
        if (id == null || id.isBlank()) {
            id = "usuario-" + System.currentTimeMillis();
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Double getDistancia() { return distancia; }
    public void setDistancia(Double distancia) { this.distancia = distancia; }

    public Double getTaxaEntrega() { return taxaEntrega; }
    public void setTaxaEntrega(Double taxaEntrega) { this.taxaEntrega = taxaEntrega; }

    public Boolean getIsAdmin() { return isAdmin; }
    public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }

    public Credenciais getCredenciais() { return credenciais; }
    public void setCredenciais(Credenciais credenciais) { this.credenciais = credenciais; }
}
