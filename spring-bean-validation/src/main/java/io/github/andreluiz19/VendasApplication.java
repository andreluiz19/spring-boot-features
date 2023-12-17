package io.github.andreluiz19;


import io.github.andreluiz19.domain.entity.Cliente;
import io.github.andreluiz19.domain.entity.Produto;
import io.github.andreluiz19.domain.repository.ClienteRepository;
import io.github.andreluiz19.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class VendasApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
