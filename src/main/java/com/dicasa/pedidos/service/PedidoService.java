package com.dicasa.pedidos.service;

import com.dicasa.pedidos.model.Pedido;
import com.dicasa.pedidos.repository.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public List<Pedido> listarTodos() {
        return repository.findAllByOrderByDataHoraDesc();
    }

    public Pedido criar(Pedido pedido) {
        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido sem itens não é permitido.");
        }
        if (pedido.getTotal() == null || pedido.getTotal() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido com valor inválido.");
        }
        pedido.setId(null); // garante novo ID gerado pelo @PrePersist
        return repository.save(pedido);
    }

    public Pedido atualizarStatus(String id, Map<String, Object> updates) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        if (updates.containsKey("status")) {
            pedido.setStatus((String) updates.get("status"));
        }
        if (updates.containsKey("horarioEntrega")) {
            Object he = updates.get("horarioEntrega");
            if (he instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> horario = (Map<String, Object>) he;
                pedido.setHorarioEntrega(horario);
            } else {
                pedido.setHorarioEntrega(null);
            }
        }

        return repository.save(pedido);
    }
}
