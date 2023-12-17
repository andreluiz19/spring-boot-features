package io.github.andreluiz19.domain.repository;

import io.github.andreluiz19.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
