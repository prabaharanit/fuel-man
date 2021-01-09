package com.mayavan.fuelman.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mayavan.fuelman.exception.ResourceNotFoundException;
import com.mayavan.fuelman.repo.CreditBookRepository;
import com.mayavan.fuelman.repo.model.CreditBook;
import com.mayavan.fuelman.repo.model.CreditBookMO;
import com.mayavan.fuelman.repo.model.FuelPriceMO;
import com.mayavan.fuelman.util.DateAndTimeUtility;
import com.mayavan.fuelman.util.DateAndTimeUtility.DATEFORMAT;

@Service
public class CreditBookServiceImpl implements CreditBookService {

	Logger logger = LoggerFactory.getLogger(CreditBookServiceImpl.class);

	@Autowired
	public CreditBookRepository creditBookRepository;

	@Autowired
	public VehicleService vehicleServiceImpl;

	@Autowired
	public FuelService fuelPriceServiceImpl;

	@Override
	public CreditBookMO createCreditBook(CreditBookMO creditBookMO) throws Exception {
		try {
			creditBookRepository.save(mapMOtoDO(creditBookMO, new CreditBook()));
		} catch (DataIntegrityViolationException pExp) {
			throw new Exception(pExp.getRootCause().getLocalizedMessage());
		} catch (Exception exp) {
			logger.error("Error while creating new credit entry", exp);
			throw new Exception("Error while creating new credit entry", exp);
		}
		return creditBookMO;
	}

	@Override
	public List<CreditBookMO> getAllCreditBook() throws ResourceNotFoundException {
		List<CreditBookMO> creditBookMOs = new ArrayList<CreditBookMO>();
		creditBookRepository.findAll().forEach(creditBook -> {
			CreditBookMO creditBookMO = mapDOtoMO(creditBook, new CreditBookMO());
			creditBookMOs.add(creditBookMO);
		});

		Collections.sort(creditBookMOs, new Comparator<CreditBookMO>() {
			DateFormat f = new SimpleDateFormat("dd MM yyyy HH:mm:ss");

			@Override
			public int compare(CreditBookMO o1, CreditBookMO o2) {
				try {
					return -1 * f.parse(o1.getCreated_dttm()).compareTo(f.parse(o2.getCreated_dttm()));
				} catch (ParseException e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
		creditBookMOs.forEach(obj -> System.out.println("date of sale ::::" + obj.getCreated_dttm()));
		// TODO Auto-generated method stub
		return creditBookMOs;
	}

	@Override
	public CreditBookMO getCreditBookForId(Integer Id) throws ResourceNotFoundException {
		CreditBook creditBook = creditBookRepository.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("No credit book found for this id :: " + Id));
		return mapDOtoMO(creditBook, new CreditBookMO());
	}

	@Override
	public CreditBookMO updateCreditBook(CreditBookMO creditBookMO) throws ResourceNotFoundException {
		CreditBook creditBook = creditBookRepository.findById(creditBookMO.getId()).orElseThrow(
				() -> new ResourceNotFoundException("No credit book found for this id ::" + creditBookMO.getId()));
		creditBookRepository.save(mapMOtoDO(creditBookMO, creditBook));
		return creditBookMO;
	}

	private CreditBookMO mapDOtoMO(CreditBook creditBook, CreditBookMO creditBookMO) {
		creditBookMO.setId(creditBook.getId());
		creditBookMO.setLitre_sale_volume(creditBook.getLitre_sale_volume());
		creditBookMO.setAmount_of_sale(creditBook.getAmount_of_sale());
		FuelPriceMO fuelPriceMO = new FuelPriceMO();
		try {
			creditBookMO.setVehicleMO(vehicleServiceImpl.getVehicleById(creditBook.getVh_id()));
		} catch (ResourceNotFoundException exp) {
			new ResourceNotFoundException("no vehilce found for id =" + creditBook.getVh_id());
		}
		try {
			fuelPriceMO.setFuel_type(fuelPriceServiceImpl.getFuelTypeById(creditBook.getFuel_type_id()));
		} catch (ResourceNotFoundException e) {
			new ResourceNotFoundException("No fuel type found for id =" + creditBook.getFuel_type_id());
		}
		fuelPriceMO.setPrice(creditBook.getFuelPrice());
		creditBookMO.setFuelPriceMO(fuelPriceMO);
		try {
			creditBookMO.setDttm_of_sale(DateAndTimeUtility.changeDateFormat(creditBook.getDttm_of_sale().toString(),
					DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT_DTTM_OF_SALE));
			creditBookMO.setIs_paid(creditBook.getIs_paid());
			if (creditBook.getCreated_dttm() != null)
				creditBookMO
						.setCreated_dttm(DateAndTimeUtility.changeDateFormat(creditBook.getCreated_dttm().toString(),
								DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT_DTTM_OF_SALE));
			if (creditBook.getModified_dttm() != null)
				creditBookMO
						.setModified_dttm(DateAndTimeUtility.changeDateFormat(creditBook.getModified_dttm().toString(),
								DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT_DTTM_OF_SALE));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return creditBookMO;
	}

	private CreditBook mapMOtoDO(CreditBookMO creditBookMO, CreditBook creditBook) {
		creditBook.setId(creditBookMO.getId());
		creditBook.setVh_id(creditBookMO.getVehicleMO().getId());
		creditBook.setFuel_type_id(creditBookMO.getFuelPriceMO().getFuel_type().getId());
		creditBook.setLitre_sale_volume(creditBookMO.getLitre_sale_volume());
		creditBook.setAmount_of_sale(creditBookMO.getAmount_of_sale());
		creditBook.setFuelPrice(creditBookMO.getFuelPriceMO().getPrice());
		try {
			creditBook.setDttm_of_sale(DateAndTimeUtility.convertStringToTimestamp(creditBookMO.getDttm_of_sale(),
					DATEFORMAT.CLIENT_DATE_TIME_FORMAT));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		creditBook.setIs_paid(creditBookMO.getIs_paid());
		creditBook.setComments(creditBookMO.getComments());
		return creditBook;
	}
}
