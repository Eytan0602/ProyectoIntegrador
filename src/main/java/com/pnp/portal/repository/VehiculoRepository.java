// 3. VehiculoRepository.java
package com.pnp.portal.repository;

import com.pnp.portal.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
}