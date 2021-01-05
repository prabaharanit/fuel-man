/**
 * 
 */
package com.mayavan.fuelman.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mayavan.fuelman.repo.model.Vehicle;

/**
 * @author praba
 *
 */
public interface VehicleRepository extends JpaRepository<Vehicle, Integer>{

}
