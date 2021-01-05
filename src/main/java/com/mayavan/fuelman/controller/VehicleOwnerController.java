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
import com.mayavan.fuelman.repo.VehicleOwnerRepository;
import com.mayavan.fuelman.repo.model.VehicleOwner;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1")
public class VehicleOwnerController {
	
	Logger logger = LoggerFactory.getLogger(VehicleOwnerController.class);

	@Autowired
	private VehicleOwnerRepository vehicleOwnerRepository;

	
	@GetMapping("/vehicleowners")
	public List<VehicleOwner> getAllVehicleOwners() {
		System.out.println("inside get all vehicle owner");
		return vehicleOwnerRepository.findAll();
	}
	
	@GetMapping("/vehicleOwner/{id}")
	public ResponseEntity<VehicleOwner> getVehicleOwnerById(@PathVariable(value = "id") int vehicleOwnerId)
			throws ResourceNotFoundException {
		VehicleOwner vehilceOwner = vehicleOwnerRepository.findById(vehicleOwnerId)
				.orElseThrow(() -> new ResourceNotFoundException("Vehicle Owner not found for this id :: " + vehicleOwnerId));
		return ResponseEntity.ok().body(vehilceOwner);
	}

	@PostMapping("/vehicleowner")
	public VehicleOwner createVehicleOwner(@Valid @RequestBody VehicleOwner vehicleOwner) {
		try {
			vehicleOwner = vehicleOwnerRepository.save(vehicleOwner);
		} catch (Exception exp) {
			logger.info("error while saving vehicle owner details", exp);
		}

		return vehicleOwner;
	}
	
	
	@PutMapping("/vehicleOwner/{id}")
	public ResponseEntity<VehicleOwner> updateFuelType(@PathVariable(value = "id") int vehicleOwnerId,
			@Valid @RequestBody VehicleOwner vehicleOwnerDetails) throws ResourceNotFoundException {
		VehicleOwner vehicleOwner = vehicleOwnerRepository.findById(vehicleOwnerId)
				.orElseThrow(() -> new ResourceNotFoundException("Vehicle Owner not found for this id :: " + vehicleOwnerId));

		vehicleOwner.setName(vehicleOwnerDetails.getName());
		vehicleOwner.setMobile_no(vehicleOwnerDetails.getMobile_no());
		vehicleOwner.setTransport_name(vehicleOwnerDetails.getTransport_name());
		vehicleOwner.setIs_deleted(vehicleOwnerDetails.getIs_deleted());
		vehicleOwner.setCreated_dttm(vehicleOwnerDetails.getCreated_dttm());
		vehicleOwner.setModified_dttm(vehicleOwnerDetails.getModified_dttm());
		final VehicleOwner updatedvehicleOwner = vehicleOwnerRepository.save(vehicleOwner);
		return ResponseEntity.ok(updatedvehicleOwner);
	}
}
