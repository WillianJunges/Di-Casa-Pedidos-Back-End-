package com.dicasa.pedidos.service;

import com.dicasa.pedidos.model.Configuracoes;
import com.dicasa.pedidos.repository.ConfiguracoesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ConfiguracoesService {

    private final ConfiguracoesRepository repository;

    public ConfiguracoesService(ConfiguracoesRepository repository) {
        this.repository = repository;
    }

    public Configuracoes obter() {
        return repository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Configurações não encontradas"));
    }

    public Configuracoes atualizar(Configuracoes novas) {
        repository.findAll().stream()
                .findFirst()
                .ifPresent(existente -> novas.setId(existente.getId()));
        return repository.save(novas);
    }
}
