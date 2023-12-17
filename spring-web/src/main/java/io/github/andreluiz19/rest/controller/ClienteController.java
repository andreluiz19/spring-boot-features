//package io.github.andreluiz19.rest.controller;
//
//import io.github.andreluiz19.domain.entity.Cliente;
//import io.github.andreluiz19.domain.repository.ClienteJpaRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.ExampleMatcher;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/api/clientes")
//public class ClienteController {
//
//    @Autowired
//    private ClienteJpaRepository clienteJpaRepository;
//
//    @RequestMapping(value = "/hello/{nome}", // "value" recebe um Array, então pode-se definir mais de uma URL para o mesmo recurso
//            method = RequestMethod.GET,
//            consumes = {"application/json", "application/xml"}, // Tipo de objeto que recebe
//            produces = {"application/json", "application/xml"}) // Tipo de objeto que envia
//    @ResponseBody
//    public String helloClients(@PathVariable("nome") String nomeCliente){
//        return String.format("Hello %s", nomeCliente);
//    }
//
//    @GetMapping("/{id}")
//    @ResponseBody
//    public ResponseEntity getClienteById(@PathVariable("id") Integer id) {
//        Optional<Cliente> cliente = clienteJpaRepository.findById(id);
//
//        if( cliente.isPresent()) {
//            // return ResponseEntity.ok(cliente.get());
//            ResponseEntity<Cliente> responseEntity = new ResponseEntity<>(cliente.get(), HttpStatus.OK);
//            return responseEntity;
//        }
//
//        return ResponseEntity.notFound().build();
//
//    }
//
//    @PostMapping
//    @ResponseBody
//    public ResponseEntity save(@RequestBody Cliente cliente) {
//        Cliente c = clienteJpaRepository.save(cliente);
//        return ResponseEntity.ok(c);
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseBody
//    public ResponseEntity delete(@PathVariable Integer id) {
//        Optional<Cliente> cliente = clienteJpaRepository.findById(id);
//
//        if(cliente.isPresent()) {
//            clienteJpaRepository.delete(cliente.get());
//            return ResponseEntity.noContent().build();
//        }
//
//        return ResponseEntity.notFound().build();
//    }
//
//    @PutMapping("/{id}")
//    @ResponseBody
//    public ResponseEntity update(@PathVariable("id") Integer id,
//                                 @RequestBody Cliente cliente) {
//        return clienteJpaRepository
//                .findById(id)
//                .map(clienteExistente -> {
//                    cliente.setId(clienteExistente.getId());
//                    clienteJpaRepository.save(cliente);
//                    return ResponseEntity.noContent().build();
//                }).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping
//    @ResponseBody
//    public ResponseEntity find( Cliente cliente) {
//        ExampleMatcher matcher = ExampleMatcher
//                .matching()
//                .withIgnoreCase()
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Configurações de busca
//
//        Example example = Example.of(cliente, matcher); // Criando query com os filtros que o obeto possuí
//
//        List<Cliente> clientes = clienteJpaRepository.findAll(example);
//
//        return ResponseEntity.ok(clientes);
//    }
//}
