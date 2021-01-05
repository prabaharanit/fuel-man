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

import com.mayavan.fuelman.exception.ResourceNotFoundException;
import com.mayavan.fuelman.exception.UniqueConstraintException;
import com.mayavan.fuelman.repo.model.CreditBookMO;
import com.mayavan.fuelman.service.CreditBookService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1")
public class CreditBookController {

	Logger logger = LoggerFactory.getLogger(CreditBookController.class);

	@Autowired
	private CreditBookService creditBookServiceImpl;

	// credit book

	@PostMapping("/creditEntry")
	public CreditBookMO createCreditBook(@Valid @RequestBody CreditBookMO creditBookMO)
			throws UniqueConstraintException {
		creditBookMO = creditBookServiceImpl.createCreditBook(creditBookMO);
		return creditBookMO;
	}

	@GetMapping("/creditEntries")
	public List<CreditBookMO> getAllCreditBook() throws ResourceNotFoundException {
		System.out.println("inside get all credit book");
		return creditBookServiceImpl.getAllCreditBook();
	}

	@GetMapping("/creditEntry/{id}")
	public ResponseEntity<CreditBookMO> getCreditBookById(@PathVariable(value = "id") int creditBookId)
			throws ResourceNotFoundException {
		CreditBookMO creditBookMO = creditBookServiceImpl.getCreditBookForId(creditBookId);
		return ResponseEntity.ok().body(creditBookMO);
	}

	@PutMapping("/creditEntry/{id}")
	public ResponseEntity<CreditBookMO> updateVehicle(@PathVariable(value = "id") int vehicleId,
			@Valid @RequestBody CreditBookMO creditBookMO) throws ResourceNotFoundException {
		creditBookServiceImpl.updateCreditBook(creditBookMO);
		return ResponseEntity.ok(creditBookMO);
	}
	// credit book

}
