package io.github.andreluiz19.domain.repository;

import io.github.andreluiz19.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
