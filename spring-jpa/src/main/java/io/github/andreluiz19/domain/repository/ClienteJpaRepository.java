package io.github.andreluiz19.domain.repository;

import io.github.andreluiz19.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClienteJpaRepository extends JpaRepository<Cliente, Integer> {
    // JpaRepository já tem dentro dele um EntityManager
    // Recebe como parâmetro da entidade: Cliente e o tipo da primary key dela: Integer
    // Não precisa do @Repository

    // Ex: SELECT c FROM Cliente c WHERE c.nome LIKE :nome
    // Query Method
    List<Cliente> findByNomeContaining(String nome); // usar "findBY" + atributo da base de dados + especificação
    // OBS: pode-se usar os atributos SQL para montar a query

    // Query Method
    boolean existsByNome(String nome);

    // Query personalizada
    //@Query(value = "SELECT c FROM Cliente c WHERE c.nome LIKE %:nome%") // HQL
    @Query(value = "SELECT * FROM CLIENTE WHERE NOME LIKE %:nome%;", nativeQuery = true) // SQL
    List<Cliente> encontrarPorNome(@Param("nome") String nome); // @Param deve ter o mesmo nome do parâmetro da query tanto HQL quanto SQL

    // Query Method
    //void deleteByName(String nome);

    // Query personalizadas sobrescrevendo Query Methods
    @Query(value = "DELETE FROM Cliente c WHERE c.nome = :nome", nativeQuery = true)
    @Modifying // Caso queira personalizar um Query Methods, deve-se usar essa anotação
    @Transactional
    void deleteByName(@Param("nome") String nome);

    @Query(value = "SELECT c FROM Cliente c LEFT JOIN FETCH c.pedidos WHERE c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);

}
