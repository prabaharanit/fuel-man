package com.mayavan.fuelman.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mayavan.fuelman.controller.model.FuelPriceMO;
import com.mayavan.fuelman.controller.model.FuelTypeMO;
import com.mayavan.fuelman.exception.ResourceNotFoundException;
import com.mayavan.fuelman.exception.UniqueConstraintException;
import com.mayavan.fuelman.repo.FuelPriceRepository;
import com.mayavan.fuelman.repo.FuelTypeRepository;
import com.mayavan.fuelman.repo.model.FuelPrice;
import com.mayavan.fuelman.repo.model.FuelType;
import com.mayavan.fuelman.util.DateAndTimeUtility;
import com.mayavan.fuelman.util.DateAndTimeUtility.DATEFORMAT;

@Service
public class FuelServiceImpl implements FuelService {

	Logger logger = LoggerFactory.getLogger(FuelServiceImpl.class);

	@Autowired
	private FuelPriceRepository fuelPriceRepository;

	@Autowired
	private FuelTypeRepository fuelTypeRepository;
	
	
	@Override
	public FuelTypeMO getFuelTypeById(int fueltypeId) throws ResourceNotFoundException {
		FuelType fuelType = fuelTypeRepository.findById(fueltypeId)
				.orElseThrow(() -> new ResourceNotFoundException("FuelType not found for this id :: " + fueltypeId));
		return mapDOtoMO(fuelType, new FuelTypeMO());
	}

	@Override
	public FuelPriceMO getFuelPriceById(int fuelPriceId) throws ResourceNotFoundException {
		FuelPriceMO fuelPriceMO = null;
		try {
			FuelPrice fuelPrice = fuelPriceRepository.findById(fuelPriceId).orElseThrow(
					() -> new ResourceNotFoundException("FuelType not found for this id :: " + fuelPriceId));
			fuelPriceMO = mapDOtoMO(new FuelPriceMO(), fuelPrice);
		} catch (ResourceNotFoundException e) {
			new ResourceNotFoundException("FuelType not found for this id :: " + fuelPriceId);
		}

		return fuelPriceMO;
	}

	@Override
	public FuelPriceMO getFuelPriceByDateTime(String dateTime, int fuelTypeId) throws Exception {
		FuelPriceMO fuelPriceMO = null;
		try {
			Date fuelPriceDateTime = DateAndTimeUtility.convertStringToDate(dateTime,
					DATEFORMAT.CLIENT_DATE_TIME_FORMAT_DTTM_OF_SALE, DATEFORMAT.DB_DATE_TIME_FORMAT);
			FuelPrice fuelPrice = fuelPriceRepository.findFuelPriceMatchingDateTime(fuelPriceDateTime, fuelTypeId);
			if (fuelPrice == null) {
				throw new Exception("No fuel price found for date time range " + dateTime + " else add fuel price or enter price value manually ");
			}
			fuelPriceMO = mapDOtoMO(new FuelPriceMO(), fuelPrice);
		} catch (ParseException parExp) {
			throw new Exception("Error while parsing datetime " + dateTime, parExp);
		}
		return fuelPriceMO;
	}

	@Override
	public FuelPriceMO createFuelPrice(FuelPriceMO fuelPriceMO) throws UniqueConstraintException {
		try {
			FuelPrice fuelPrice = fuelPriceRepository.save(mapMOtoDO(fuelPriceMO, new FuelPrice()));
			System.out.println("************************* fuel type id ******************" + fuelPrice.getId());
			fuelPriceMO.setId(fuelPrice.getId());
		} catch (DataIntegrityViolationException dIExp) {
			logger.error("fuel type and date of sale unique key constraint", dIExp);
			throw new UniqueConstraintException("Fuel type and date of sale alreay exists");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fuelPriceMO;
	}

	@Override
	public List<FuelPriceMO> getAllFuelPrices() {
		List<FuelPriceMO> fuelPriceMOs = new ArrayList<FuelPriceMO>();
		fuelPriceRepository.findAll().forEach(e -> {
			FuelPriceMO fuelPriceMO = mapDOtoMO(new FuelPriceMO(), e);
			try {
				FuelType fuelType = fuelTypeRepository.findById(e.getFuel_type_id())
						.orElseThrow(() -> new ResourceNotFoundException(
								"FuelType not found for this id :: " + e.getFuel_type_id()));
				fuelPriceMO.setFuel_type(mapDOtoMO(fuelType, new FuelTypeMO()));
			} catch (ResourceNotFoundException e1) {
				new ResourceNotFoundException("FuelType not found for this id :: " + e.getFuel_type_id());
			}
			fuelPriceMOs.add(fuelPriceMO);

		});

		Collections.sort(fuelPriceMOs, new Comparator<FuelPriceMO>() {
			DateFormat f = new SimpleDateFormat("dd.MM.yyyy");

			@Override
			public int compare(FuelPriceMO o1, FuelPriceMO o2) {
				try {
					return -1 * f.parse(o1.getDate_of_sale()).compareTo(f.parse(o2.getDate_of_sale()));
				} catch (ParseException e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
		fuelPriceMOs.forEach(obj -> System.out.println("date of sale" + obj.getDate_of_sale()));
		return fuelPriceMOs;
	}

	@Override
	public Map<String, Boolean> deleteFuelPrice(int fuelPriceId) {
		FuelPrice fuelPrice = null;
		try {
			fuelPrice = fuelPriceRepository.findById(fuelPriceId).orElseThrow(
					() -> new ResourceNotFoundException("Fuel Price not found for this id :: " + fuelPriceId));
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fuelPriceRepository.delete(fuelPrice);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted successfully", Boolean.TRUE);
		return response;
	}

	@Override
	public FuelPriceMO updateFuelPrice(FuelPriceMO fuelPriceMO) throws UniqueConstraintException {
		FuelPrice fuelPrice = null;
		try {
			fuelPrice = fuelPriceRepository.findById(fuelPriceMO.getId()).orElseThrow(
					() -> new ResourceNotFoundException("FuelType not found for this id :: " + fuelPriceMO.getId()));

			fuelPrice = mapMOtoDO(fuelPriceMO, fuelPrice);
			fuelPriceRepository.save(fuelPrice);
		} catch (DataIntegrityViolationException dIExp) {
			logger.error("fuel type and date of sale unique key constraint", dIExp);
			throw new UniqueConstraintException("Fuel type and date of sale combination alreay exists");
		} catch (Exception exp) {
			logger.error("Error while updating fueltypes", exp);
		}
		return fuelPriceMO;
	}

	private FuelPrice mapMOtoDO(FuelPriceMO fuelPriceMO, FuelPrice fuelPrice) throws Exception {
		System.out.println("fuel type date " + fuelPriceMO.getDate_of_sale());
		fuelPrice.setId(fuelPriceMO.getId());
		fuelPrice.setFuel_type_id(fuelPriceMO.getFuel_type().getId());
		fuelPrice.setPrice(fuelPriceMO.getPrice());
		fuelPrice.setDate_of_sale(DateAndTimeUtility.convertStringToDate(fuelPriceMO.getDate_of_sale(),
				DATEFORMAT.CLIENT_FORMAT_DD_MM_YYYY, DATEFORMAT.DB_FORMAT_YYYY_DD_MM));
		fuelPrice.setIs_deleted(fuelPriceMO.getIs_deleted());
		try {
			System.out.println("from dttm " + fuelPriceMO.getFrom_dttm());
			if (fuelPriceMO.getFrom_dttm() != null) {
				fuelPrice.setFrom_dttm(DateAndTimeUtility.convertStringToTimestamp(fuelPriceMO.getFrom_dttm(),
						DATEFORMAT.CLIENT_DATE_TIME_FORMAT));
			}
			if (fuelPriceMO.getTo_dttm() != null) {
				fuelPrice.setTo_dttm(DateAndTimeUtility.convertStringToTimestamp(fuelPriceMO.getTo_dttm(),
						DATEFORMAT.CLIENT_DATE_TIME_FORMAT));
			}
		} catch (ParseException parExp) {
			logger.error("Error while parsing string to timestamp ", parExp);
			throw new ParseException("Error while parsing string to timestamp", 0);
		}
		return fuelPrice;
	}

	private FuelTypeMO mapDOtoMO(FuelType fuelType, FuelTypeMO fuelTypeMO){
		fuelTypeMO.setId(fuelType.getId());
		fuelTypeMO.setName(fuelType.getName());
		fuelTypeMO.setType(fuelType.getType());
		fuelTypeMO.setIs_deleted(fuelType.getIs_deleted());
		fuelTypeMO.setCreated_dttm(fuelType.getCreated_dttm());
		fuelTypeMO.setModified_dttm(fuelType.getModified_dttm());
		return fuelTypeMO;
	}
	
	private FuelPriceMO mapDOtoMO(FuelPriceMO fuelPriceMO, FuelPrice fuelPrice) {
		fuelPriceMO.setId(fuelPrice.getId());
		fuelPriceMO.setPrice(fuelPrice.getPrice());
		FuelType fuelType = new FuelType();
		fuelType.setId(fuelPrice.getFuel_type_id());
		fuelPriceMO.setFuel_type(mapDOtoMO(fuelType, new FuelTypeMO()));
		try {
			System.out.println("date of sale from db format ::: " + fuelPrice.getDate_of_sale());
			fuelPriceMO.setDate_of_sale(DateAndTimeUtility.changeDateFormat(fuelPrice.getDate_of_sale().toString(),
					DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_FORMAT_DD_MM_YYYY));
			if (fuelPrice.getCreated_dttm() != null)
				fuelPriceMO
						.setCreated_dttm(DateAndTimeUtility
								.changeDateFormat(fuelPrice.getCreated_dttm().toString(),
										DATEFORMAT.DB_FORMAT_YYYY_DD_MM, DATEFORMAT.CLIENT_FORMAT_DD_MM_YYYY)
								.toString());
			if (fuelPrice.getModified_dttm() != null)
				fuelPriceMO
						.setModified_dttm(DateAndTimeUtility
								.changeDateFormat(fuelPrice.getModified_dttm().toString(),
										DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_FORMAT_DD_MM_YYYY)
								.toString());
			if (fuelPrice.getFrom_dttm() != null)
				fuelPriceMO.setFrom_dttm(DateAndTimeUtility.changeDateFormat(fuelPrice.getFrom_dttm().toString(),
						DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT).toString());
			if (fuelPrice.getTo_dttm() != null)
				fuelPriceMO.setTo_dttm(DateAndTimeUtility.changeDateFormat(fuelPrice.getTo_dttm().toString(),
						DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT).toString());
			System.out.println(" *****************fuel price to dttm is *****************" + fuelPriceMO.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(" ***************************************************************"
				+ "***************************" + fuelPrice.getCreated_dttm());
		fuelPriceMO.setIs_deleted(fuelPrice.getIs_deleted());
		return fuelPriceMO;
	}

}
