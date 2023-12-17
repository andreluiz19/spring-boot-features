package io.github.andreluiz19;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// Annotations padrão Spring
//@Configuration // Define que o objeto é uma definição de Beans
// Definindo que essa configuração só vai estar disponível no perfil de desenvolvimento (development)
// OBS: Utilziar o nome após o "-" do application.properties (development)
//@Profile("development")

// Annotation personalizada
@Development
public class MyConfiguration {

    @Bean(name = "applicationName") // OBS: Se não tiver o name = "" utiliza o nome do método/variável
    public String applicationName() {
        return "Sistema de Vendas";
    }

    @Bean
    public CommandLineRunner executar() { // Método que é executado quando roda a aplicação. Executa um trecho de código.
        return args -> {
            System.out.println("Rodando a configuração de Desenvolvimento!");
        };
    }
}
