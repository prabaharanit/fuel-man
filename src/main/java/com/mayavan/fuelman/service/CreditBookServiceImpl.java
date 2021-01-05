package com.mayavan.fuelman.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mayavan.fuelman.exception.ResourceNotFoundException;
import com.mayavan.fuelman.repo.CreditBookRepository;
import com.mayavan.fuelman.repo.model.CreditBook;
import com.mayavan.fuelman.repo.model.CreditBookMO;

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
	public CreditBookMO createCreditBook(CreditBookMO creditBookMO) {
		try {
			creditBookRepository.save(mapMOtoDO(creditBookMO, new CreditBook()));
		} catch (Exception exp) {
			logger.error("Error while saving credit entry", exp);
		}
		return creditBookMO;
	}

	@Override
	public List<CreditBookMO> getAllCreditBook() throws ResourceNotFoundException {
		List<CreditBookMO> creditBookMOs = new ArrayList<CreditBookMO>();
		creditBookRepository.findAll().forEach(creditBook -> {
			CreditBookMO creditBookMO = mapDOtoMO(creditBook, new CreditBookMO());
			try {
				creditBookMO.setVehicleMO(vehicleServiceImpl.getVehicleById(creditBook.getVh_id()));
			} catch (ResourceNotFoundException exp) {
				new ResourceNotFoundException("no vehilce found for id =" + creditBook.getVh_id());
			}
			try {
				creditBookMO.setFuelPriceMO(fuelPriceServiceImpl.getFuelPriceById(creditBook.getFuel_type_id()));
			} catch (ResourceNotFoundException e) {
				new ResourceNotFoundException(
						"No daily fuel price found for id =" + creditBook.getFuel_type_id());
			}
			creditBookMOs.add(creditBookMO);
		});

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
		creditBookRepository.findById(creditBookMO.getId()).orElseThrow(() -> new ResourceNotFoundException("No credit book found for this id ::" + creditBookMO.getId()));
		creditBookRepository.save(mapMOtoDO(creditBookMO, new CreditBook()));
		return creditBookMO;
	}

	private CreditBookMO mapDOtoMO(CreditBook creditBook, CreditBookMO creditBookMO) {
		creditBookMO.setLitre_sale_volume(creditBook.getLitre_sale_volume());
		creditBookMO.setAmount_of_sale(creditBook.getAmount_of_sale());
		creditBookMO.setDttm_of_sale(creditBook.getDttm_of_sale());
		creditBookMO.setIs_paid(creditBook.getIs_paid());
		creditBookMO.setCreated_dttm(creditBook.getCreated_dttm());
		creditBookMO.setModified_dttm(creditBook.getModified_dttm());
		return creditBookMO;
	}

	private CreditBook mapMOtoDO(CreditBookMO creditBookMO, CreditBook creditBook) {
		creditBook.setId(creditBookMO.getId());
		creditBook.setVh_id(creditBookMO.getVehicleMO().getId());
		creditBook.setFuel_type_id(creditBookMO.getFuelPriceMO().getFuel_type().getId());
		creditBook.setLitre_sale_volume(creditBookMO.getLitre_sale_volume());
		creditBook.setAmount_of_sale(creditBookMO.getAmount_of_sale());
		creditBook.setDttm_of_sale(creditBookMO.getDttm_of_sale());
		creditBook.setIs_paid(creditBookMO.getIs_paid());
		creditBook.setCreated_dttm(creditBookMO.getCreated_dttm());
		creditBook.setModified_dttm(creditBookMO.getModified_dttm());
		return creditBook;
	}
}
