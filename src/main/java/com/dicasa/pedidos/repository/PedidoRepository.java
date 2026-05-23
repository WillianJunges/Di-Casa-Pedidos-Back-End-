package com.dicasa.pedidos.repository;

import com.dicasa.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, String> {

    List<Pedido> findAllByOrderByDataHoraDesc();
}
