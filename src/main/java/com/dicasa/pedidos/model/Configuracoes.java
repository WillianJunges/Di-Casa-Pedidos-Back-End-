package com.dicasa.pedidos.model;

import com.dicasa.pedidos.converter.JsonListConverter;
import com.dicasa.pedidos.converter.JsonMapConverter;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "configuracoes")
public class Configuracoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private Double latitude;
    private Double longitude;

    @Column(name = "raio_entrega_gratis")
    private Double raioEntregaGratis = 5.0;

    @Column(name = "taxa_por_km")
    private Double taxaPorKm = 1.0;

    @Column(name = "filtrar_por_dia")
    private Boolean filtrarPorDia = true;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "horario_funcionamento", columnDefinition = "TEXT")
    private Map<String, Object> horarioFuncionamento;

    @Convert(converter = JsonListConverter.class)
    @Column(name = "chaves_pix", columnDefinition = "TEXT")
    private List<Object> chavesPix;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getRaioEntregaGratis() { return raioEntregaGratis; }
    public void setRaioEntregaGratis(Double raioEntregaGratis) { this.raioEntregaGratis = raioEntregaGratis; }

    public Double getTaxaPorKm() { return taxaPorKm; }
    public void setTaxaPorKm(Double taxaPorKm) { this.taxaPorKm = taxaPorKm; }

    public Boolean getFiltrarPorDia() { return filtrarPorDia; }
    public void setFiltrarPorDia(Boolean filtrarPorDia) { this.filtrarPorDia = filtrarPorDia; }

    public Map<String, Object> getHorarioFuncionamento() { return horarioFuncionamento; }
    public void setHorarioFuncionamento(Map<String, Object> horarioFuncionamento) { this.horarioFuncionamento = horarioFuncionamento; }

    public List<Object> getChavesPix() { return chavesPix; }
    public void setChavesPix(List<Object> chavesPix) { this.chavesPix = chavesPix; }
}
