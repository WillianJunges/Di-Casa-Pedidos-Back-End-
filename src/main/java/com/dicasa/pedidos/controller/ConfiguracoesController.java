package com.dicasa.pedidos.controller;

import com.dicasa.pedidos.model.Configuracoes;
import com.dicasa.pedidos.service.ConfiguracoesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configuracoes")
public class ConfiguracoesController {

    private final ConfiguracoesService service;

    public ConfiguracoesController(ConfiguracoesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Configuracoes> obter() {
        return ResponseEntity.ok(service.obter());
    }

    @PutMapping
    public ResponseEntity<Configuracoes> atualizar(@RequestBody Configuracoes configuracoes) {
        return ResponseEntity.ok(service.atualizar(configuracoes));
    }
}
