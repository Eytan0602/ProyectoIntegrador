// 2. LicenciaRepository.java
package com.pnp.portal.repository;

import com.pnp.portal.model.Licencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenciaRepository extends JpaRepository<Licencia, Long> {
    boolean existsByNumeroLicencia(String numeroLicencia);
    boolean existsByDni(String dni); // opcional si también quieres validar el DNI
}
