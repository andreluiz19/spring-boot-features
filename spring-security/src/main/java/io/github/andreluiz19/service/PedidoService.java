package io.github.andreluiz19.service;

import io.github.andreluiz19.domain.entity.Pedido;
import io.github.andreluiz19.domain.enums.StatusPedido;
import io.github.andreluiz19.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO pedidoDTO);

    Optional<Pedido> obeterPedidoCompleto(Integer id);

    void updateStatus(Integer id, StatusPedido status);
}
