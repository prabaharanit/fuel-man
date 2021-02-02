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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mayavan.fuelman.controller.model.FuelPriceMO;
import com.mayavan.fuelman.controller.model.FuelTypeMO;
import com.mayavan.fuelman.exception.ResourceNotFoundException;
import com.mayavan.fuelman.exception.UniqueConstraintException;
import com.mayavan.fuelman.repo.FuelTypeRepository;
import com.mayavan.fuelman.repo.model.FuelType;
import com.mayavan.fuelman.service.FuelService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1")
public class FuelTypeController {

	Logger logger = LoggerFactory.getLogger(FuelTypeController.class);

	@Autowired
	private FuelTypeRepository fuelTypeRepository;

	@Autowired
	private FuelService fuelService;

	@GetMapping("/fueltypes")
	public List<FuelTypeMO> getAllFuelTypes() {
		System.out.println("inside get all fuel types");
		return fuelService.getAllFuelType();
	}

	@GetMapping("/fueltypes/{id}")
	public ResponseEntity<FuelTypeMO> getFuelTypeById(@PathVariable(value = "id") int fueltypeId)
			throws ResourceNotFoundException {
		FuelTypeMO fuelTypeMO = fuelService.getFuelTypeById(fueltypeId);
		return ResponseEntity.ok().body(fuelTypeMO);
	}

	@PostMapping("/fueltypes")
	public FuelType createFuelType(@Valid @RequestBody FuelType fueltype) {
		try {
			fueltype = fuelTypeRepository.save(fueltype);
		} catch (Exception exp) {
			logger.info("error while saving fueltypes", exp);
		}

		return fueltype;
	}

	@PutMapping("/fueltypes/{id}")
	public ResponseEntity<FuelType> updateFuelType(@PathVariable(value = "id") int fueltypeId,
			@Valid @RequestBody FuelType fueltypeDetails) throws ResourceNotFoundException {
		FuelType fueltype = fuelTypeRepository.findById(fueltypeId)
				.orElseThrow(() -> new ResourceNotFoundException("FuelType not found for this id :: " + fueltypeId));

		fueltype.setType(fueltypeDetails.getType());
		fueltype.setName(fueltypeDetails.getName());
		fueltype.setIs_deleted(fueltypeDetails.getIs_deleted());
		fueltype.setCreated_dttm(fueltypeDetails.getCreated_dttm());
		fueltype.setModified_dttm(fueltypeDetails.getModified_dttm());
		final FuelType updatedFuelType = fuelTypeRepository.save(fueltype);
		return ResponseEntity.ok(updatedFuelType);
	}

	@DeleteMapping("/fueltypes/{id}")
	public Map<String, Boolean> deleteFuelType(@PathVariable(value = "id") int fueltypeId)
			throws ResourceNotFoundException {
		FuelType fueltype = fuelTypeRepository.findById(fueltypeId)
				.orElseThrow(() -> new ResourceNotFoundException("FuelType not found for this id :: " + fueltypeId));

		fuelTypeRepository.delete(fueltype);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	/*********************** Fuel Price Mappings 
	 * @throws ResourceNotFoundException ****************/

	@GetMapping("/fuelPrice/{id}")
	public FuelPriceMO getFuelPriceById(@PathVariable(value = "id") int fuelPriceId) throws ResourceNotFoundException {
		FuelPriceMO fuelPriceMO = fuelService.getFuelPriceById(fuelPriceId);
		return fuelPriceMO;
	}
	
	@PostMapping("/fuelPrice")
	public FuelPriceMO createFuelPrice(@Valid @RequestBody FuelPriceMO fuelPriceMO) throws UniqueConstraintException {
		fuelPriceMO = fuelService.createFuelPrice(fuelPriceMO);
		return fuelPriceMO;
	}

	@GetMapping("/fuelPrices")
	public List<FuelPriceMO> getAllFuelPrice() {
		List<FuelPriceMO> fuelPrices = null;
		try {
			fuelPrices = fuelService.getAllFuelPrices();
		} catch (Exception exp) {
			logger.error("error while getting all fuel prices", exp);
		}

		return fuelPrices;
	}

	@PutMapping("/fuelPrice/{id}")
	public ResponseEntity<FuelPriceMO> updateFuelPrice(@PathVariable(value = "id") int fuelPriceId,
			@Valid @RequestBody FuelPriceMO fuelPriceMODetails) throws UniqueConstraintException {
		fuelService.updateFuelPrice(fuelPriceMODetails);
		return ResponseEntity.ok(fuelPriceMODetails);
	}

	@DeleteMapping("/fuelPrice/{id}")
	public Map<String, Boolean> deleteFuelPrice(@PathVariable(value = "id") int fueltypeId) {
		try {
			Map<String, Boolean> response = fuelService.deleteFuelPrice(fueltypeId);
			return response;
		} catch (Exception exp) {
			logger.error("error while dele	ting fuel price id =" + fueltypeId, exp);
		}
		return null;
	}
	
	@GetMapping("/fuelPriceFromDttmOfSale")
	public FuelPriceMO getFuelPriceFromDateTimeOfSale(@RequestParam("dttm_of_sale") String dateTimeOfSale, @RequestParam("id") int fuelTypeId) throws Exception {
		System.out.println(" get fuel price from date time of sale date value is = " + dateTimeOfSale);
		FuelPriceMO fuelPriceMO = fuelService.getFuelPriceByDateTime(dateTimeOfSale, fuelTypeId);
		return fuelPriceMO;
	}
}
