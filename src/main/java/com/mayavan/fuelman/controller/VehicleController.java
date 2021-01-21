package com.mayavan.fuelman.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayavan.fuelman.exception.ResourceNotFoundException;
import com.mayavan.fuelman.exception.UniqueConstraintException;
import com.mayavan.fuelman.repo.VehicleTypeRepository;
import com.mayavan.fuelman.repo.model.VehicleMO;
import com.mayavan.fuelman.repo.model.VehicleType;
import com.mayavan.fuelman.repo.model.VehicleTypeMO;
import com.mayavan.fuelman.service.VehicleService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1")
public class VehicleController {

	Logger logger = LoggerFactory.getLogger(VehicleController.class);

	@Autowired
	private VehicleTypeRepository vehicleTypeRepository;

	@Autowired
	private VehicleService vehicleServiceImpl;

	// vehicle

	@PostMapping("/vehicle")
	public VehicleMO createVehicle(@Valid @RequestBody VehicleMO vehicleMO) throws UniqueConstraintException {
		System.out.println("inside add vehicle" + vehicleMO.getNumberPlate() + vehicleMO.getVhOwner().getName()
				+ vehicleMO.getVhType().getType());
		try {
			vehicleMO = vehicleServiceImpl.createVehicle(vehicleMO);
		} catch(Exception exp){
			
		}
		return vehicleMO;
	}

	@GetMapping("/vehicles")
	public List<VehicleMO> getAllVehicle() {
		System.out.println("inside get all vehilce");
		return vehicleServiceImpl.getAllVehicles();
	}

	@GetMapping("/vehicle/{id}")
	public ResponseEntity<VehicleMO> getVehicleById(@PathVariable(value = "id") int vehicleTypeId)
			throws ResourceNotFoundException {
		VehicleMO vehicleMO = vehicleServiceImpl.getVehicleById(vehicleTypeId);
		return ResponseEntity.ok().body(vehicleMO);
	}

	@PutMapping("/vehicle/{id}")
	public ResponseEntity<VehicleMO> updateVehicle(@PathVariable(value = "id") int vehicleId,
			@Valid @RequestBody VehicleMO vehicleMO) throws UniqueConstraintException {
		vehicleServiceImpl.updateVehicle(vehicleMO);
		return ResponseEntity.ok(vehicleMO);
	}
	// vehicle

	// vehicle type

	@GetMapping("/vehicleTypes")
	public List<VehicleTypeMO> getAllVehicleType() {
		System.out.println("inside get all vehilce types");
		return vehicleServiceImpl.getAllVehicleType();
	}

	@GetMapping("/vehicleType/{id}")
	public ResponseEntity<VehicleType> getVehicleTypeById(@PathVariable(value = "id") int vehicleTypeId)
			throws ResourceNotFoundException {
		VehicleType vehicleType = vehicleTypeRepository.findById(vehicleTypeId).orElseThrow(
				() -> new ResourceNotFoundException("VehicleType not found for this id :: " + vehicleTypeId));
		return ResponseEntity.ok().body(vehicleType);
	}

	@PostMapping("/vehicleType")
	public VehicleType createVehicleType(@Valid @RequestBody VehicleType vehicleType) {
		try {
			vehicleType = vehicleTypeRepository.save(vehicleType);
		} catch (Exception exp) {
			logger.info("error while saving VehicleType", exp);
		}

		return vehicleType;
	}

	@PutMapping("/vehicleType/{id}")
	public ResponseEntity<VehicleType> updateVehicleType(@PathVariable(value = "id") int vehicleTypeId,
			@Valid @RequestBody VehicleType vehicleTypeDetails) throws ResourceNotFoundException {
		VehicleType VehicleType = vehicleTypeRepository.findById(vehicleTypeId).orElseThrow(
				() -> new ResourceNotFoundException("VehicleType not found for this id :: " + vehicleTypeId));

		VehicleType.setType(vehicleTypeDetails.getType());
		VehicleType.setName(vehicleTypeDetails.getName());
		VehicleType.setIs_deleted(vehicleTypeDetails.getIs_deleted());
		VehicleType.setCreated_dttm(vehicleTypeDetails.getCreated_dttm());
		VehicleType.setModified_dttm(vehicleTypeDetails.getModified_dttm());
		final VehicleType updatedVehicleType = vehicleTypeRepository.save(VehicleType);
		return ResponseEntity.ok(updatedVehicleType);
	}

	@DeleteMapping("/vehicleType/{id}")
	public Map<String, Boolean> deleteVehicleType(@PathVariable(value = "id") int vehicleTypeId)
			throws ResourceNotFoundException {
		VehicleType vehicleType = vehicleTypeRepository.findById(vehicleTypeId).orElseThrow(
				() -> new ResourceNotFoundException("VehicleType not found for this id :: " + vehicleTypeId));
		vehicleTypeRepository.delete(vehicleType);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
