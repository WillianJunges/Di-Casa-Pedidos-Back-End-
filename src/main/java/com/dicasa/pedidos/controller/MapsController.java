package com.dicasa.pedidos.controller;

import com.dicasa.pedidos.service.MapsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/maps")
public class MapsController {

    private final MapsService mapsService;

    public MapsController(MapsService mapsService) {
        this.mapsService = mapsService;
    }

    @GetMapping("/distancia")
    public ResponseEntity<?> calcularDistancia(@RequestParam String endereco) {
        if (endereco == null || endereco.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Endereço não informado"));
        }
        try {
            return ResponseEntity.ok(mapsService.calcularDistancia(endereco));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("erro", e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", e.getMessage()));
        }
    }
}
