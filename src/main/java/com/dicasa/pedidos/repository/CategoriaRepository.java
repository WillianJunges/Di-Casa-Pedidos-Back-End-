package com.dicasa.pedidos.repository;

import com.dicasa.pedidos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByAtivoTrueOrderByOrdemAsc();
    boolean existsByNomeIgnoreCase(String nome);
}
