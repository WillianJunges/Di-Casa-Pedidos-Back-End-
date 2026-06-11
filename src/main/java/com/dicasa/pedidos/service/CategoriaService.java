package com.dicasa.pedidos.service;

import com.dicasa.pedidos.model.Categoria;
import com.dicasa.pedidos.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Categoria> listarAtivas() {
        return repository.findByAtivoTrueOrderByOrdemAsc();
    }

    public List<Categoria> listarTodas() {
        return repository.findAll();
    }

    public Categoria criar(Categoria categoria) {
        return repository.save(categoria);
    }

    public Categoria atualizar(Long id, Categoria dados) {
        Categoria existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
        existente.setNome(dados.getNome());
        existente.setEmoji(dados.getEmoji());
        existente.setAtivo(dados.getAtivo());
        existente.setOrdem(dados.getOrdem());
        return repository.save(existente);
    }

    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
        }
        repository.deleteById(id);
    }
}
