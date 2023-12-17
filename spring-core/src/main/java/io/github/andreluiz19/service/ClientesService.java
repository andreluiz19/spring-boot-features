package io.github.andreluiz19.service;

import io.github.andreluiz19.model.Cliente;
import io.github.andreluiz19.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Classe ou camada que contém as regras de negócio
public class ClientesService {

    @Autowired
    private ClientesRepository clientesRepository;

    /*
     @Autowired // Injeção por construtor
     public ClientesService(ClientesRepository clientesRepository) {
         this.clientesRepository = clientesRepository;
     }
    */

    public void salvarCliente(Cliente cliente) {
        validarCliente(cliente);
        clientesRepository.save(cliente);
    }

    public void validarCliente(Cliente cliente) {
        // Aplica Validações
    }
}
