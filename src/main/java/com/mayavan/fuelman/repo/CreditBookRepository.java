/**
 * 
 */
package com.mayavan.fuelman.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mayavan.fuelman.repo.model.CreditBook;

/**
 * @author praba
 *
 */
public interface CreditBookRepository extends JpaRepository<CreditBook, Integer>{
	
	@Query(" From credit_book t where t.vehicleId in :ids" )
	List<CreditBook> findByVehicleIdIn(@Param("ids") List<Integer> vehicleIds);

}
