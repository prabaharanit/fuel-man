package com.mayavan.fuelman.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mayavan.fuelman.controller.model.VehicleMO;
import com.mayavan.fuelman.controller.model.VehicleOwnerMO;
import com.mayavan.fuelman.controller.model.VehicleTypeMO;
import com.mayavan.fuelman.exception.ResourceNotFoundException;
import com.mayavan.fuelman.exception.UniqueConstraintException;
import com.mayavan.fuelman.repo.VehicleRepository;
import com.mayavan.fuelman.repo.VehicleTypeRepository;
import com.mayavan.fuelman.repo.model.Vehicle;
import com.mayavan.fuelman.repo.model.VehicleType;
import com.mayavan.fuelman.util.DateAndTimeUtility;
import com.mayavan.fuelman.util.DateAndTimeUtility.DATEFORMAT;

@Service
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private VehicleOwnerServiceImpl vehicleOwnerServiceImpl;

	@Autowired
	private VehicleTypeRepository vehicleTypeRepository;

	Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

	@Override
	public VehicleMO createVehicle(VehicleMO vehicleMO) throws UniqueConstraintException {
		try {
			System.out.println("inside add vehicle service impl");
			Vehicle vehicle = vehicleRepository.save(mapMOtoDO(vehicleMO, new Vehicle()));
			vehicleMO.setId(vehicle.getId());
		} catch (DataIntegrityViolationException dIExp) {
			logger.error("vehilce number unique key constraint", dIExp);
			throw new UniqueConstraintException("Vehicle Number alreay exists");
		} catch (Exception exp) {
			System.out.println(exp);
			logger.error("Error while creating vehicle", exp);
		}
		return vehicleMO;
	}

	@Override
	public List<VehicleMO> getAllVehicles() {
		List<VehicleMO> vehicleMOs = new ArrayList<VehicleMO>();
		vehicleRepository.findAll().forEach(vehicle -> {
			VehicleMO vehicleMO = mapDOtoMO(vehicle, new VehicleMO());
			vehicleMOs.add(vehicleMO);
		});

		return vehicleMOs;
	}

	@Override
	public List<Integer> getAllVehicleByVhOwner(int vhOwnerId) {
		List<Integer> vehicleIds = new ArrayList<Integer>();
		vehicleRepository.findAllVehilceByVhOwner(vhOwnerId).forEach(vehicle -> {
			
			vehicleIds.add(vehicle.getId());
		});
		return vehicleIds;
	}
	
	@Override
	public VehicleMO getVehicleById(int id) throws ResourceNotFoundException {
		Vehicle vehicle = vehicleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Vehicle not found for this id :: " + id));
		VehicleMO vehicleMO = mapDOtoMO(vehicle, new VehicleMO());
		return vehicleMO;
	}

	@Override
	public Map<String, Boolean> deleteVehicle(int Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VehicleMO updateVehicle(VehicleMO vehicleMO) throws UniqueConstraintException {
		Vehicle vehicle = null;
		try {
			vehicle = vehicleRepository.findById(vehicleMO.getId()).orElseThrow(
					() -> new ResourceNotFoundException("Vehicle not found for this id :: " + vehicleMO.getId()));

			vehicle = mapMOtoDO(vehicleMO, vehicle);
			vehicleRepository.save(vehicle);
		} catch (DataIntegrityViolationException dIExp) {
			logger.error("vehilce number unique key constraint", dIExp);
			throw new UniqueConstraintException("Vehicle Number alreay exists");
		} catch (Exception exp) {
			logger.error("Error while updating Vehicle", exp);
		}
		return vehicleMO;
	}

	private Vehicle mapMOtoDO(VehicleMO vehicleMO, Vehicle vehicle) {
		vehicle.setId(vehicleMO.getId());
		vehicle.setVhTypeId(vehicleMO.getVhType().getId());
		vehicle.setVhOwnerId(vehicleMO.getVhOwner().getId());
		vehicle.setNumberPlate(vehicleMO.getNumberPlate());
		vehicle.setIs_deleted(vehicleMO.getIs_deleted());
		return vehicle;
	}

	private VehicleMO mapDOtoMO(Vehicle vehicle, VehicleMO vehicleMO) {
		vehicleMO.setId(vehicle.getId());
		vehicleMO.setNumberPlate(vehicle.getNumberPlate());
		try {
			VehicleOwnerMO vhOwner = vehicleOwnerServiceImpl.getVehicleOwnerById(vehicle.getVhOwnerId());
			vehicleMO.setVhOwner(vhOwner);
		} catch (ResourceNotFoundException e1) {
			new ResourceNotFoundException("Vehicle Owner not found for id :: " + vehicle.getVhOwnerId());
		}
		try {
			VehicleType vhType = vehicleTypeRepository.findById(vehicle.getVhTypeId()).orElseThrow(
					() -> new ResourceNotFoundException("Vehicle type not found for  id :: " + vehicle.getVhTypeId()));
			vehicleMO.setVhType(vhType);
		} catch (ResourceNotFoundException rExp) {
			new ResourceNotFoundException("Vehicle type not found for id :: " + vehicle.getVhTypeId());
		}

		vehicleMO.setIs_deleted(vehicle.getIs_deleted());
		try {
			if(vehicle.getCreated_dttm() != null)
			vehicleMO.setCreated_dttm(DateAndTimeUtility.changeDateFormat(vehicle.getCreated_dttm().toString(), DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT));
			if(vehicle.getModified_dttm() != null)
			vehicleMO.setModified_dttm(DateAndTimeUtility.changeDateFormat(vehicle.getModified_dttm().toString(), DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vehicleMO;
	}

	private VehicleTypeMO mapDOtoMO(VehicleType vehicleType, VehicleTypeMO vehicleTypeMO) {
		vehicleTypeMO.setId(vehicleType.getId());
		vehicleTypeMO.setType(vehicleType.getType());
		vehicleTypeMO.setName(vehicleType.getName());
		vehicleTypeMO.setIs_deleted(vehicleType.getIs_deleted());
		try {
			if (vehicleType.getCreated_dttm() != null)
			vehicleTypeMO.setCreated_dttm(DateAndTimeUtility.changeDateFormat(vehicleType.getCreated_dttm().toString(),
					DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT));
			if (vehicleType.getModified_dttm() != null)
			vehicleTypeMO
					.setModified_dttm(DateAndTimeUtility.changeDateFormat(vehicleType.getModified_dttm().toString(),
							DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT));
		} catch (ParseException parseExp) {
			parseExp.printStackTrace();
			logger.error("Error while mapping vehicle type do to mo", parseExp);
		}
		return vehicleTypeMO;
	}

	@Override
	public List<VehicleTypeMO> getAllVehicleType() {
		List<VehicleTypeMO> vehicleTypeMOLst = new ArrayList<VehicleTypeMO>();
		try {
			vehicleTypeRepository.findAll().forEach(vehicleType -> {
				vehicleTypeMOLst.add(mapDOtoMO(vehicleType, new VehicleTypeMO()));
			});
		} catch (Exception exp) {
			exp.printStackTrace();
			logger.error("Error while mapping vehicle type do to mo", exp);
		}

		return vehicleTypeMOLst;
	}

}
