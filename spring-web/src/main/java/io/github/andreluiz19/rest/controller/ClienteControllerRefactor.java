package io.github.andreluiz19.rest.controller;

import io.github.andreluiz19.domain.entity.Cliente;
import io.github.andreluiz19.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController // Não precisa mais utilizar o @ResponseBody
@RequestMapping("/api/clientes")
public class ClienteControllerRefactor{

    @Autowired
    private ClienteRepository clienteRepository;

    @RequestMapping(value = "/hello/{nome}", // "value" recebe um Array, então pode-se definir mais de uma URL para o mesmo recurso
            method = RequestMethod.GET,
            consumes = {"application/json", "application/xml"}, // Tipo de objeto que recebe
            produces = {"application/json", "application/xml"}) // Tipo de objeto que envia
    public String helloClients(@PathVariable("nome") String nomeCliente){
        return String.format("Hello %s", nomeCliente);
    }

    @GetMapping("{id}")
    public Cliente getById(@PathVariable("id") Integer id) {
        return clienteRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
                });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Código que vai ser retornado quando criar o Cliente
    public Cliente save(@RequestBody Cliente cliente) {
       return clienteRepository.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return cliente;
                }).orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
                });
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Integer id,
                       @RequestBody Cliente cliente) {
        clienteRepository
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clienteRepository.save(cliente);
                    return clienteExistente;
                }).orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
                });
    }

    @GetMapping
    public List<Cliente> find(Cliente cliente) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Configurações de busca

        Example example = Example.of(cliente, matcher); // Criando query com os filtros que o obeto possuí

        return clienteRepository.findAll(example);
    }
}
