package io.github.andreluiz19.domain.repository;

import io.github.andreluiz19.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper; // Mapeia o resultado de uma consulta para uma classe, no caso a classe Cliente
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClienteRepository {

    private static final String INSERT = "INSERT INTO CLIENTE (NOME) VALUES (?);";
    private static final String SELECT_ALL = "SELECT * FROM CLIENTE;";
    private static final String UPDATE = "UPDATE CLIENTE SET NOME = ? WHERE ID = ?;";
    private static final String DELETE = "DELETE FROM CLIENTE WHERE ID = ?;";
    private static final String SELECT_BY_NAME = "SELECT * FROM CLIENTE WHERE NOME LIKE ?;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Cliente save(Cliente cliente) {
        jdbcTemplate.update(INSERT, cliente.getNome());
        return cliente;
    }

    public Cliente update(Cliente cliente) {
        jdbcTemplate.update(UPDATE, cliente.getNome(), cliente.getId());
        return cliente;
    }

    public void delete(Cliente cliente) {
        delete(cliente.getId());
    }

    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }

    public List<Cliente> findByName(String nome) {
        return jdbcTemplate.query(SELECT_BY_NAME, new Object[]{"%" + nome + "%"}, getRowMapper());
    }

    public List<Cliente> getAll() {
        return jdbcTemplate.query(SELECT_ALL, getRowMapper());
    }

    private static RowMapper<Cliente> getRowMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer id = resultSet.getInt("id"); // Pega a coluna ID do ResultSet
                String nome = resultSet.getString("nome"); // Pega a coluna nome do ResultSet
                return new Cliente(id, nome);
            }
        };
    }
}
