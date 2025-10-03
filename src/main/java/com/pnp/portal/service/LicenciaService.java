package com.pnp.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pnp.portal.repository.LicenciaRepository;
import com.pnp.portal.model.Licencia;          
@Service
public class LicenciaService {

    @Autowired
    private LicenciaRepository licenciaRepository;

    public Licencia registrarLicencia(Licencia dto) {
        // 🔍 Validación de duplicado
        if (licenciaRepository.existsByNumeroLicencia(dto.getNumeroLicencia())) {
            throw new IllegalArgumentException("El número de licencia ya existe");
        }

        // Convertir DTO a entidad
        Licencia licencia = new Licencia();
        licencia.setNumeroLicencia(dto.getNumeroLicencia());
        licencia.setNombreCompleto(dto.getNombreCompleto());
        licencia.setCategoria(dto.getCategoria());
        licencia.setDni(dto.getDni());
        licencia.setFechaExpedicion(dto.getFechaExpedicion());
        licencia.setFechaVencimiento(dto.getFechaVencimiento());
        licencia.setRestricciones(dto.getRestricciones());
        licencia.setEstado(dto.getEstado());

        // Guardar en BD
        return licenciaRepository.save(licencia);
    }
}
