package com.mayavan.fuelman.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mayavan.fuelman.exception.ResourceNotFoundException;
import com.mayavan.fuelman.exception.UniqueConstraintException;
import com.mayavan.fuelman.repo.model.VehicleMO;
import com.mayavan.fuelman.repo.model.VehicleTypeMO;

@Service
public interface VehicleService {
	
	public VehicleMO createVehicle(VehicleMO vehicleMO) throws UniqueConstraintException;
	
	public List<VehicleMO> getAllVehicles();
	
	public VehicleMO getVehicleById(int Id)throws ResourceNotFoundException;
	
	public Map<String, Boolean> deleteVehicle(int Id);
	
	public VehicleMO updateVehicle(VehicleMO vehicleMO)throws UniqueConstraintException;
	
	public List<VehicleTypeMO> getAllVehicleType();
	
	public List<Integer> getAllVehicleByVhOwner(int vhOwnerId) throws Exception;

}
