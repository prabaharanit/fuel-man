/**
 * 
 */
package com.mayavan.fuelman.repo;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mayavan.fuelman.repo.model.FuelPrice;

/**
 * @author praba
 * Fuelprice repository to manage daily fuel price
 */
public interface FuelPriceRepository extends JpaRepository<FuelPrice, Integer> {
	
	@Query(" FROM daily_fuel_price t where t.from_dttm <= ?1 and t.to_dttm >= ?1 and fuel_type_id = ?2")
	FuelPrice findFuelPriceMatchingDateTime(Date dateTime, int fuel_type_id);
	


}
