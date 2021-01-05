/**
 * 
 */
package com.mayavan.fuelman.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mayavan.fuelman.repo.model.FuelType;

/**
 * @author praba
 *
 */
public interface FuelTypeRepository extends JpaRepository<FuelType, Integer>{

}
