package com.pnp.portal.security;

import com.pnp.portal.model.Usuario;
import com.pnp.portal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + dni));

        return User
                .withUsername(usuario.getDni())
                .password(usuario.getPassword())
                .roles("USER")
                .build();
    }
}