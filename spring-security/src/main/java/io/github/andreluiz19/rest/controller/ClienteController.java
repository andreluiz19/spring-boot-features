package io.github.andreluiz19.rest.controller;

import io.github.andreluiz19.domain.entity.Cliente;
import io.github.andreluiz19.domain.repository.ClienteRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController // Não precisa mais utilizar o @ResponseBody
@RequestMapping("/api/clientes")
@Api(value = "/api/clientes", description = "Clientes API")
public class ClienteController {

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
    @ApiOperation("Obtém um Cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado."),
            @ApiResponse(code = 404, message = "Cliente não encontrado.")
    })
    public Cliente getById(@PathVariable("id") @ApiParam("ID do Cliente") Integer id) {
        return clienteRepository
                .findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Código que vai ser retornado quando criar o Cliente
    @ApiOperation("Salva um Cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo."),
            @ApiResponse(code = 400, message = "Erro ao cadastrar Cliente.")
    })
    public Cliente save(@RequestBody @Valid Cliente cliente) {
       return clienteRepository.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Deleta um Cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente deletado."),
            @ApiResponse(code = 400, message = "Erro ao deletar Cliente.")
    })
    public void delete(@PathVariable Integer id) {
        clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return cliente;
                }).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualiza um Cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente atualizado."),
            @ApiResponse(code = 400, message = "Erro ao atualizar Cliente.")
    })
    public void update(@PathVariable("id") Integer id,
                       @RequestBody @Valid Cliente cliente) {
        clienteRepository
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clienteRepository.save(cliente);
                    return clienteExistente;
                }).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
    }

    @GetMapping
    @ApiOperation("Obtém todos os Clientes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Todos os Clientes foram encontrados."),
            @ApiResponse(code = 404, message = "Não há Clientes cadastrados.")
    })
    public List<Cliente> find(Cliente cliente) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Configurações de busca

        Example example = Example.of(cliente, matcher); // Criando query com os filtros que o objeto possuí

        return clienteRepository.findAll(example);
    }
}
