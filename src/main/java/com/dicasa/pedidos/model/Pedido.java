package com.dicasa.pedidos.model;

import com.dicasa.pedidos.converter.JsonListConverter;
import com.dicasa.pedidos.converter.JsonMapConverter;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    private String id;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "usuario_json", columnDefinition = "TEXT")
    private Map<String, Object> usuario;

    @Convert(converter = JsonListConverter.class)
    @Column(name = "itens_json", columnDefinition = "TEXT")
    private List<Object> itens;

    private Double subtotal;

    @Column(name = "taxa_entrega")
    private Double taxaEntrega;

    private Double total;

    @Column(name = "metodo_pagamento")
    private String metodoPagamento;

    @Column(name = "tipo_entrega")
    private String tipoEntrega;

    @Column(name = "data_hora")
    private String dataHora;

    private String status = "PENDENTE";

    @Column(name = "troco_para")
    private Double trocoPara;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "horario_entrega", columnDefinition = "TEXT")
    private Map<String, Object> horarioEntrega;

    @PrePersist
    public void prePersist() {
        if (id == null || id.isBlank()) {
            id = "PED-" + System.currentTimeMillis();
        }
        if (status == null) status = "PENDENTE";
        if (dataHora == null) dataHora = OffsetDateTime.now().toString();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Map<String, Object> getUsuario() { return usuario; }
    public void setUsuario(Map<String, Object> usuario) { this.usuario = usuario; }

    public List<Object> getItens() { return itens; }
    public void setItens(List<Object> itens) { this.itens = itens; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Double getTaxaEntrega() { return taxaEntrega; }
    public void setTaxaEntrega(Double taxaEntrega) { this.taxaEntrega = taxaEntrega; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }

    public String getTipoEntrega() { return tipoEntrega; }
    public void setTipoEntrega(String tipoEntrega) { this.tipoEntrega = tipoEntrega; }

    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getTrocoPara() { return trocoPara; }
    public void setTrocoPara(Double trocoPara) { this.trocoPara = trocoPara; }

    public Map<String, Object> getHorarioEntrega() { return horarioEntrega; }
    public void setHorarioEntrega(Map<String, Object> horarioEntrega) { this.horarioEntrega = horarioEntrega; }
}
