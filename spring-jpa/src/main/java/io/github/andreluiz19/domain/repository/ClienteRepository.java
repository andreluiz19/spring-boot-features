package io.github.andreluiz19.domain.repository;

import io.github.andreluiz19.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ClienteRepository {

    @Autowired
    private EntityManager entityManager; // Responsável por realizar as operações na base pelo JPA

    @Transactional // Necessário em todo método que vai realizar operações na base
    public Cliente save(Cliente cliente) {
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente update(Cliente cliente) {
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void delete(Cliente cliente) {
        if(!entityManager.contains(cliente)) {
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }

    @Transactional
    public void delete(Integer id) {
        Cliente cliente = entityManager.find(Cliente.class, id);
        delete(cliente);
    }

    @Transactional(readOnly = true) // Apenas leitura
    public List<Cliente> findByName(String nome) {
        String jpql = "SELECT c FROM Cliente c WHERE c.nome LIKE :nome";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome","%" + nome + "%");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Cliente> getAll() {
        return entityManager
                .createQuery("from Cliente", Cliente.class)
                .getResultList();
    }
}
