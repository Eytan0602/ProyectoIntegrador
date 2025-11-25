package com.pnp.portal.repository;

import com.pnp.portal.model.PermisoLunasPolarizadas;
import com.pnp.portal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermisoRepository extends JpaRepository<PermisoLunasPolarizadas, Long> {

    Optional<PermisoLunasPolarizadas> findByDni(String dni);

    Optional<PermisoLunasPolarizadas> findByPlacaVehiculo(String placaVehiculo);

    List<PermisoLunasPolarizadas> findByEstado(PermisoLunasPolarizadas.EstadoTramite estado);
    
    // Nuevos métodos para filtrar por usuario
    List<PermisoLunasPolarizadas> findByUsuario(Usuario usuario);
    
    List<PermisoLunasPolarizadas> findByUsuarioOrderByFechaSolicitudDesc(Usuario usuario);
    
    Optional<PermisoLunasPolarizadas> findByDniAndUsuario(String dni, Usuario usuario);
}