// 4. SoatRepository.java
package com.pnp.portal.repository;

import com.pnp.portal.model.Soat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoatRepository extends JpaRepository<Soat, Long> {
}