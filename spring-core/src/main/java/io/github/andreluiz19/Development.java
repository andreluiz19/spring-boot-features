package io.github.andreluiz19;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Annotations padrão de uma Anotação Personalizada
@Target(ElementType.TYPE) // Elegível apenas para classes, interfaces e ENUMS
@Retention(RetentionPolicy.RUNTIME)

// Configuration que usar a annotation @Development herdará essas annotations
@Configuration
@Profile("development")
public @interface Development {

}
