package com.pnp.portal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(userDetailsService)
                   .passwordEncoder(passwordEncoder())
                   .and()
                   .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Recursos públicos
                .requestMatchers(
                    "/", 
                    "/index", 
                    "/registro", 
                    "/login",
                    "/error",
                    "/css/**", 
                    "/js/**", 
                    "/JS/**",
                    "/imagenes/**", 
                    "/images/**",
                    "/static/**", 
                    "/webjars/**",
                    "/resources/**", 
                    "/*.css", 
                    "/*.js",
                    "/*.png", 
                    "/*.jpg", 
                    "/*.jpeg", 
                    "/*.gif",
                    "/favicon.ico"
                ).permitAll()
                
                // Rutas protegidas
                .requestMatchers("/dni", "/seguimiento-tramite").authenticated()
                
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index?logout=true")
                .permitAll()
            )
            // ⭐ DESACTIVAR CSRF TEMPORALMENTE
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}