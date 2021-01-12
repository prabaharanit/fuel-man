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
import com.mayavan.fuelman.repo.model.VehicleOwnerMO;
import com.mayavan.fuelman.service.VehicleOwnerServiceImpl;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1")
public class VehicleOwnerController {
	
	Logger logger = LoggerFactory.getLogger(VehicleOwnerController.class);

	@Autowired
	private VehicleOwnerServiceImpl vehicleOwnerServiceImpl;

	
	@GetMapping("/vehicleowners")
	public List<VehicleOwnerMO> getAllVehicleOwners() {
		System.out.println("inside get all vehicle owner");
		return vehicleOwnerServiceImpl.getAllVehicleOwner();
	}
	
	@GetMapping("/vehicleOwner/{id}")
	public ResponseEntity<VehicleOwnerMO> getVehicleOwnerById(@PathVariable(value = "id") int vehicleOwnerId)
			throws ResourceNotFoundException {
		return ResponseEntity.ok().body(vehicleOwnerServiceImpl.getVehicleOwnerById(vehicleOwnerId));
	}

	@PostMapping("/vehicleowner")
	public VehicleOwnerMO createVehicleOwner(@Valid @RequestBody VehicleOwnerMO vehicleOwnerMO) throws UniqueConstraintException {
		vehicleOwnerServiceImpl.createVehicleOwner(vehicleOwnerMO);
		return vehicleOwnerMO;
	}
	
	
	@PutMapping("/vehicleOwner/{id}")
	public ResponseEntity<VehicleOwnerMO> updateVehicleOwner(@PathVariable(value = "id") int vehicleOwnerId,
			@Valid @RequestBody VehicleOwnerMO vehicleOwnerDetails) throws ResourceNotFoundException, UniqueConstraintException {
		return ResponseEntity.ok(vehicleOwnerServiceImpl.updateVehicleOwner(vehicleOwnerDetails));
	}
}
