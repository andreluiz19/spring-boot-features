package io.github.andreluiz19.domain.repository;

import io.github.andreluiz19.domain.entity.Cliente;
import io.github.andreluiz19.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    Set<Pedido> findByCliente(Cliente cliente);
}
