package io.github.andreluiz19;


import io.github.andreluiz19.domain.entity.Cliente;
import io.github.andreluiz19.domain.entity.Pedido;
import io.github.andreluiz19.domain.repository.ClienteJpaRepository;
import io.github.andreluiz19.domain.repository.ClienteRepository;
import io.github.andreluiz19.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VendasApplication {

    // Utilizando Spring Data
    @Bean
    CommandLineRunner init(@Autowired ClienteJpaRepository clienteJpaRepository,
                           @Autowired PedidoRepository pedidoRepository) {
        return args -> {
            System.out.println("\nSalvando Clientes");
            Cliente primeiroCliente = new Cliente("André");
            clienteJpaRepository.save(primeiroCliente); // save() cria e atualiza
            // clienteJpaRepository.save(new Cliente("Pedro")); // save() cria e atualiza

            Pedido p = new Pedido();
            p.setCliente(primeiroCliente);
            p.setDataPedido(LocalDate.now());
            p.setTotal(BigDecimal.valueOf(100));
            pedidoRepository.save(p);

            System.out.println("\nBuscando Cliente e Pedidos");
            Cliente cliente = clienteJpaRepository.findClienteFetchPedidos(primeiroCliente.getId());
            System.out.println("Cliente: " + cliente);
            System.out.println("Pedidos: " + cliente.getPedidos());

            System.out.println("\nBuscando Pedidos por Cliente");
            pedidoRepository.findByCliente(primeiroCliente).forEach(System.out::println);
//            System.out.println("\nBuscando Todos Clientes");
//            List<Cliente> todosClientes = clienteJpaRepository.findAll();
//            todosClientes.forEach(System.out::println);

//            System.out.println("\nAtualizando Clientes");
//            todosClientes.forEach(c -> {
//                c.setNome(c.getNome() + " Atualizado");
//                clienteJpaRepository.save(c); // save() cria e atualiza
//            });
//            todosClientes = clienteJpaRepository.findAll();
//            todosClientes.forEach(System.out::println);

//            System.out.println("\nBuscando Por Nome");
//            boolean exists = clienteJpaRepository.existsByNome("André");
//            System.out.println("Existe um cliente com o nome André: " + exists);
//            //clienteJpaRepository.findByNomeContaining("ndr").forEach(System.out::println);
//            clienteJpaRepository.encontrarPorNome("ndr").forEach(System.out::println);

//            System.out.println("Deletendo Por Nome");
//            clienteJpaRepository.deleteByName("André");
//            System.out.println("\nDeletando Clientes");
//            clienteJpaRepository.findAll().forEach(c -> {
//                clienteJpaRepository.delete(c);
//            });
//            todosClientes = clienteJpaRepository.findAll();
//            if(todosClientes.isEmpty()) {
//                System.out.println("Nenhum Cliente Encontrado");
//            } else {
//                todosClientes.forEach(System.out::println);
//            }
        };
    }

      // Utilizando JPA - EntityManager
//    @Bean
//    CommandLineRunner init(@Autowired ClienteRepository clienteRepository) {
//        return args -> {
//            System.out.println("\nSalvando Clientes");
//            clienteRepository.save(new Cliente("André"));
//            clienteRepository.save(new Cliente("Pedro"));
//
//            System.out.println("\nBuscando Todos Clientes");
//            List<Cliente> todosClientes = clienteRepository.getAll();
//            todosClientes.forEach(System.out::println);
//
//            System.out.println("\nAtualizando Clientes");
//            todosClientes.forEach(c -> {
//                c.setNome(c.getNome() + " Atualizado");
//                clienteRepository.update(c);
//            });
//            todosClientes = clienteRepository.getAll();
//            todosClientes.forEach(System.out::println);
//
//            System.out.println("\nBuscando Por Nome");
//            clienteRepository.findByName("ndr").forEach(System.out::println);
//
//
//            System.out.println("\nDeletando Clientes");
//
//            clienteRepository.getAll().forEach(c -> {
//                clienteRepository.delete(c);
//            });
//
//            todosClientes = clienteRepository.getAll();
//            if(todosClientes.isEmpty()) {
//                System.out.println("Nenhum Cliente Encontrado");
//            } else {
//                todosClientes.forEach(System.out::println);
//            }
//        };
//    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
