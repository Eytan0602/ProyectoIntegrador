// 1. SolicitudDniRepository.java
package com.pnp.portal.repository;

import com.pnp.portal.model.SolicitudDni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudDniRepository extends JpaRepository<SolicitudDni, String> {
}