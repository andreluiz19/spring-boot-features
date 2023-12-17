package io.github.andreluiz19.domain.repository;

import io.github.andreluiz19.domain.entity.Cliente;
import io.github.andreluiz19.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    Set<Pedido> findByCliente(Cliente cliente);

    @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.itens WHERE p.id = :id")
    Optional<Pedido> findByIdFetchItens(Integer id);
}
