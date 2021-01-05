package com.mayavan.fuelman.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mayavan.fuelman.exception.ResourceNotFoundException;
import com.mayavan.fuelman.exception.UniqueConstraintException;
import com.mayavan.fuelman.repo.model.FuelPriceMO;

@Service
public interface FuelService {

	public FuelPriceMO createFuelPrice(FuelPriceMO fuelPriceMO)throws UniqueConstraintException;
	
	public List<FuelPriceMO> getAllFuelPrices();
	
	public FuelPriceMO getFuelPriceByDateTime(String dateTime, int fuelTypeId)throws Exception;
	
	public FuelPriceMO getFuelPriceById(int Id)throws ResourceNotFoundException;
	
	public Map<String, Boolean> deleteFuelPrice(int Id);
	
	public FuelPriceMO updateFuelPrice(FuelPriceMO fuelPriceMO)throws UniqueConstraintException;
}
