/**
 * 
 */
package com.mayavan.fuelman.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mayavan.fuelman.repo.model.Vehicle;

/**
 * @author praba
 *
 */
public interface VehicleRepository extends JpaRepository<Vehicle, Integer>{

	@Query(" From vehicle t where t.vhOwnerId =  ?1")
	List<Vehicle> findAllVehilceByVhOwner(int id);
}
