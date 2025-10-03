package com.pnp.portal.service;

import com.pnp.portal.model.PermisoLunasPolarizadas;
import com.pnp.portal.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    public PermisoLunasPolarizadas registrarSolicitud(PermisoLunasPolarizadas permiso) {
        permiso.setFechaSolicitud(LocalDate.now());
        permiso.setEstado(PermisoLunasPolarizadas.EstadoTramite.EN_PROCESO);
        return permisoRepository.save(permiso);
    }

    public Optional<PermisoLunasPolarizadas> consultarPorDni(String dni) {
        return permisoRepository.findByDni(dni);
    }

}
