package com.dicasa.pedidos.model;

import com.dicasa.pedidos.converter.JsonListConverter;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    private String id;

    @Column(nullable = false)
    private String nome;

    private String descricao;
    private Double preco;
    private String categoria;
    private String imagem;

    @Convert(converter = JsonListConverter.class)
    @Column(name = "dia_da_semana", columnDefinition = "TEXT")
    private List<Object> diaDaSemana = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (id == null || id.isBlank()) {
            id = "produto-" + System.currentTimeMillis();
        }
    }

    // Aceita preco como String ou Number no JSON (inconsistencia dos dados legados)
    @JsonSetter("preco")
    public void setPrecoFromJson(Object value) {
        if (value instanceof Number n) {
            this.preco = n.doubleValue();
        } else if (value instanceof String s) {
            try { this.preco = Double.parseDouble(s); } catch (NumberFormatException ignored) {}
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }

    public List<Object> getDiaDaSemana() { return diaDaSemana; }
    public void setDiaDaSemana(List<Object> diaDaSemana) { this.diaDaSemana = diaDaSemana; }
}
