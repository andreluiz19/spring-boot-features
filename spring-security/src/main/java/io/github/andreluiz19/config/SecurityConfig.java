package io.github.andreluiz19.config;

import io.github.andreluiz19.security.jwt.JwtAuthFilter;
import io.github.andreluiz19.security.jwt.JwtService;
import io.github.andreluiz19.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter();
    }

    // Autenticação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .withUser("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("USER", "ADMIN");
        auth
            .userDetailsService(usuarioService)
            .passwordEncoder(passwordEncoder());

    }

    // Autorização
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/clientes/**") // ** -> URLs que possuem parâmetros
                    .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/pedidos/**")
                    .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/produtos/**")
                    .hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/usuarios/**") // Para qualquer um coseguir registrar usuário
                    .permitAll()
                .anyRequest()
                    .authenticated()
            .and()
                //.formLogin();
                //.httpBasic();
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // STATELESS -> Cada requisição é independente
            .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * formLogin("/login.html") -> Estar dentro de resources em public ou static ou templates
     * <form method="post">
     *     <input type="text" name="username" />
     *     <input type="secret" name="password />
     *     <button type="submit"></button>
     * </form>
     */

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
