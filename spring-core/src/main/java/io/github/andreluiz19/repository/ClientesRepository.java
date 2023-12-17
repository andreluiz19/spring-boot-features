package io.github.andreluiz19.repository;

import io.github.andreluiz19.model.Cliente;
import org.springframework.stereotype.Repository;

@Repository // Classe ou camada que acessa a base de dados
// Essa anotação "traduz" as excessões que acontecem na base
public class ClientesRepository {

    public void save(Cliente cliente) {
        // Acessa a base e salva o cliente
    }
}
