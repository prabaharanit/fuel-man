package com.mayavan.fuelman.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayavan.fuelman.controller.model.CreditBookMO;
import com.mayavan.fuelman.controller.model.CreditTransactionMO;
import com.mayavan.fuelman.exception.ResourceNotFoundException;
import com.mayavan.fuelman.service.CreditBookService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1")
public class CreditBookController {

	Logger log = LoggerFactory.getLogger(CreditBookController.class);

	@Autowired
	private CreditBookService creditBookServiceImpl;

	// credit book
	@PostMapping("/creditEntry")
	public CreditBookMO createCreditBook(@Valid @RequestBody CreditBookMO creditBookMO) throws Exception {
		System.out
				.println("***********************************" + creditBookMO.getFuelPriceMO().getFuel_type().getId());
		log.info("inisid create credit book");
		creditBookMO = creditBookServiceImpl.createCreditBook(creditBookMO);
		return creditBookMO;
	}

	@GetMapping("/creditEntries")
	public List<CreditBookMO> getAllCreditBook() throws Exception {
		System.out.println("inside get all credit book");
		List<CreditBookMO> creditBookMOs = null;
		try {
			creditBookMOs = creditBookServiceImpl.getAllCreditBook();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return creditBookMOs;
	}
	
	@GetMapping("/creditEntriesByVhOwner/{id}")
	public List<CreditBookMO> getAllCreditBookByVhOwner(@PathVariable(value = "id") int vhOwnerId) throws Exception {
		System.out.println("inside get all credit book for vehicle owner id " + vhOwnerId);
		List<CreditBookMO> creditBookMOs = null;
		try {
			creditBookMOs = creditBookServiceImpl.getCreditEntriesByVhOwnerId(vhOwnerId);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return creditBookMOs;
	}


	@GetMapping("/creditEntry/{id}")
	public ResponseEntity<CreditBookMO> getCreditBookById(@PathVariable(value = "id") int creditBookId)
			throws ResourceNotFoundException {
		CreditBookMO creditBookMO = creditBookServiceImpl.getCreditBookForId(creditBookId);
		return ResponseEntity.ok().body(creditBookMO);
	}

	@PutMapping("/creditEntry/{id}")
	public ResponseEntity<CreditBookMO> updateCreditEntry(@PathVariable(value = "id") int creditBookId,
			@Valid @RequestBody CreditBookMO creditBookMO) throws Exception {
		try {
			creditBookServiceImpl.updateCreditBook(creditBookMO);
		} catch (Exception exp) {
			exp.printStackTrace();
			throw new Exception("Error while updating creidt entry for id = "+ creditBookMO.getId(), exp);
		}
		return ResponseEntity.ok(creditBookMO);
	}
	
	@PostMapping("/creditTransaction")
	public CreditTransactionMO createCreditTransaction(@Valid @RequestBody CreditTransactionMO creditTransactionMO) throws Exception {
		log.info("inisid create transaction book");
		creditTransactionMO = creditBookServiceImpl.createCreditTransaction(creditTransactionMO);
		return creditTransactionMO;
	}

	@GetMapping("/creditTransactions")
	public List<CreditTransactionMO> getAllCreditTransactions() throws Exception{
		List<CreditTransactionMO> creditTransactionMOs = null;
		try {
			creditTransactionMOs = creditBookServiceImpl.getAllCreditTransaction();
		} catch (Exception exp) {
			exp.printStackTrace();
			//throw new Exception("Error while updating creidt entry for id = "+ creditBookMO.getId(), exp);
		}
		return creditTransactionMOs;
	}
}