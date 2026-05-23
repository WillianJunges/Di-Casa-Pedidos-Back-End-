package com.dicasa.pedidos.service;

import com.dicasa.pedidos.model.Configuracoes;
import com.dicasa.pedidos.repository.ConfiguracoesRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class MapsService {

    @Value("${google.maps.api.key:}")
    private String apiKey;

    private final ConfiguracoesRepository configRepo;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public MapsService(ConfiguracoesRepository configRepo) {
        this.configRepo = configRepo;
    }

    public Map<String, Object> calcularDistancia(String endereco) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "GOOGLE_MAPS_API_KEY não configurada");
        }

        Configuracoes config = configRepo.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Configurações não encontradas"));

        String origem = URLEncoder.encode(config.getEndereco(), StandardCharsets.UTF_8);
        String destino = URLEncoder.encode(endereco, StandardCharsets.UTF_8);
        String url = String.format(
                "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&key=%s&language=pt-BR&units=metric",
                origem, destino, apiKey);

        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode dados = mapper.readTree(response.body());

            if (!"OK".equals(dados.path("status").asText())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endereço de origem inválido");
            }

            JsonNode elemento = dados.path("rows").get(0).path("elements").get(0);
            if (!"OK".equals(elemento.path("status").asText())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rota não encontrada para o endereço informado");
            }

            double distanciaKm = Math.round((elemento.path("distance").path("value").asDouble() / 1000) * 10.0) / 10.0;
            int duracaoMinutos = (int) Math.ceil(elemento.path("duration").path("value").asDouble() / 60);

            return Map.of("distanciaKm", distanciaKm, "duracaoMinutos", duracaoMinutos);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao calcular distância: " + e.getMessage());
        }
    }
}
