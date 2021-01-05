/**
 * 
 */
package com.mayavan.fuelman.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mayavan.fuelman.repo.model.CreditBook;

/**
 * @author praba
 *
 */
public interface CreditBookRepository extends JpaRepository<CreditBook, Integer>{
	

}
