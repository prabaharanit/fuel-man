package com.mayavan.fuelman.service;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mayavan.fuelman.repo.model.CreditBookMO;
import com.mayavan.fuelman.repo.model.FuelPriceMO;
import com.mayavan.fuelman.repo.model.VehicleMO;
import com.mayavan.fuelman.util.DateAndTimeUtility;
import com.mayavan.fuelman.util.DateAndTimeUtility.DATEFORMAT;


@SpringBootTest
class CreditBookServiceImplTest {
	
	@Autowired
	private CreditBookService creditBookService;

	@Test
	public void testGetFuelPriceForDttmOfSale() {
		try {
			CreditBookMO creditBookMO = getCreditBookMO();
			creditBookService.createCreditBook(creditBookMO);
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
		fuelPriceMO.setId(1);
		creditBookMO.setFuelPriceMO(fuelPriceMO);
		try {
			creditBookMO.setDttm_of_sale(DateAndTimeUtility.convertStringToTimestamp("30 12 2020 13:31:16", DATEFORMAT.CLIENT_DATE_TIME_FORMAT_DTTM_OF_SALE));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return creditBookMO;
		
	}
	
}
