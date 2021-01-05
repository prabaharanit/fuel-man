/**
 * 
 */
package com.mayavan.fuelman.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mayavan.fuelman.repo.model.VehicleType;

/**
 * @author praba
 *
 */
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer>{

}
