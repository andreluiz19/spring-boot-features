package io.github.andreluiz19;


import io.github.andreluiz19.domain.entity.Cliente;
import io.github.andreluiz19.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    CommandLineRunner init(@Autowired ClienteRepository clienteRepository) {
        return args -> {
            System.out.println("\nSalvando Clientes");
            clienteRepository.save(new Cliente("André"));
            clienteRepository.save(new Cliente("Pedro"));

            System.out.println("\nBuscando Todos Clientes");
            List<Cliente> todosClientes = clienteRepository.getAll();
            todosClientes.forEach(System.out::println);

            System.out.println("\nAtualizando Clientes");
            todosClientes.forEach(c -> {
                c.setNome(c.getNome() + " Atualizado");
                clienteRepository.update(c);
            });
            todosClientes = clienteRepository.getAll();
            todosClientes.forEach(System.out::println);

            System.out.println("\nBuscando Por Nome");
            clienteRepository.findByName("ndr").forEach(System.out::println);


            System.out.println("\nDeletando Clientes");
            /*
            clienteRepository.getAll().forEach(c -> {
                clienteRepository.delete(c);
            });
            */
            todosClientes = clienteRepository.getAll();
            if(todosClientes.isEmpty()) {
                System.out.println("Nenhum Cliente Encontrado");
            } else {
                todosClientes.forEach(System.out::println);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
