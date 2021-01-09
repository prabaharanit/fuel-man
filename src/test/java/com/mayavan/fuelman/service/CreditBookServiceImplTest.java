package com.mayavan.fuelman.service;

import java.text.ParseException;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mayavan.fuelman.repo.model.CreditBookMO;
import com.mayavan.fuelman.repo.model.FuelPriceMO;
import com.mayavan.fuelman.repo.model.FuelTypeMO;
import com.mayavan.fuelman.repo.model.VehicleMO;
import com.mayavan.fuelman.util.DateAndTimeUtility;
import com.mayavan.fuelman.util.DateAndTimeUtility.DATEFORMAT;


@SpringBootTest
class CreditBookServiceImplTest {
	
	@Autowired
	private CreditBookService creditBookService;

	@Ignore
	@Transactional
	public void testGetFuelPriceForDttmOfSale() {
		try {
			CreditBookMO creditBookMO = getCreditBookMO();
			creditBookService.createCreditBook(creditBookMO);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("get fuel price for dttm for sale");
		}
	}
	
	
	@Test
	public void testAllCreditBookEntries() {
		try {
			creditBookService.getAllCreditBook();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("get fuel price for dttm for sale");
		}
	}
	
	private CreditBookMO getCreditBookMO() {
		CreditBookMO creditBookMO = new CreditBookMO();
		creditBookMO.setAmount_of_sale(23243.78);
		creditBookMO.setLitre_sale_volume(22.2);
		VehicleMO vehicleMO = new VehicleMO();
		vehicleMO.setId(1);
		creditBookMO.setVehicleMO(vehicleMO);
		FuelPriceMO fuelPriceMO = new FuelPriceMO();
		FuelTypeMO fuelType = new FuelTypeMO();
		fuelType.setId(2);
		fuelPriceMO.setFuel_type(fuelType);
		fuelPriceMO.setPrice(78);
		creditBookMO.setFuelPriceMO(fuelPriceMO);
		creditBookMO.setComments("test data");
		try {
			creditBookMO.setDttm_of_sale(DateAndTimeUtility.changeDateFormat("30 12 2020 13:31:16",
					DATEFORMAT.DB_DATE_TIME_FORMAT, DATEFORMAT.CLIENT_DATE_TIME_FORMAT_DTTM_OF_SALE));
			try {
				creditBookService.createCreditBook(creditBookMO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return creditBookMO;
		
	}
	
}
