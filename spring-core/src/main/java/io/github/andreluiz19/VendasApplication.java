package io.github.andreluiz19;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController // Mandar mensagens para o navegador através dessa classe
//@ComponentScan
// Específica quais classes deve escanear
//@ComponentScan(basePackages = {
//     "io.github.andreluiz19.service", "io.github.andreluiz19.repository" // Array de diretório de pacotes
//})
// Não tem necessidade de usar caso o pacote esteja dentro de "o.github.andreluiz19" ou para frente.
// Pode ser usado caso precise de objetos de outros pacotes
public class VendasApplication {

    // Injetando proriedade/configuração utilizando o application.properties
    @Value("${application.name}")
    private String applicationName;

    // Injetando proriedade/configuração através de BEAN, arquivo MyConfiguration.java
    /*
    @Autowired
    @Qualifier("applicationName")
    private String applicationName;
    */

    @Cachorro
    private Animal animal;

    @Bean(name = "executarAnimal")
    public CommandLineRunner executar() {
        return args -> {
            this.animal.fazerBarulho();
        };
    }

    @GetMapping("/hello") // Definindo o path da URL - endpoint
    public String helloWorld() {
        return applicationName;
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
