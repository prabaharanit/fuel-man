package com.mayavan.fuelman.service;

import java.text.ParseException;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.mayavan.fuelman.repo.model.CreditBookMO;
import com.mayavan.fuelman.repo.model.CreditTransactionMO;
import com.mayavan.fuelman.repo.model.FuelPriceMO;
import com.mayavan.fuelman.repo.model.FuelTypeMO;
import com.mayavan.fuelman.repo.model.VehicleMO;
import com.mayavan.fuelman.repo.model.VehicleOwnerMO;
import com.mayavan.fuelman.util.DateAndTimeUtility;
import com.mayavan.fuelman.util.DateAndTimeUtility.DATEFORMAT;

@SpringBootTest
class CreditBookServiceImplTest {

	@Autowired
	private CreditBookService creditBookService;

	@Test
	@Transactional
	@Rollback
	public void testGetFuelPriceForDttmOfSale() {
		try {
			CreditBookMO creditBookMO = getCreditBookMO();
			creditBookService.createCreditBook(creditBookMO);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("get fuel price for dttm for sale");
		}
	}

	@Ignore
	public void testAllCreditBookEntries() {
		try {
			creditBookService.getAllCreditBook();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("get fuel price for dttm for sale");
		}
	}

	@Ignore
	@Transactional
	@Rollback
	public void testCreateTransaction() {
		try {
			CreditTransactionMO creditTransactionMO = getCreditTransactionMO();
			creditBookService.createCreditTransaction(creditTransactionMO);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("get fuel price for dttm for sale");
		}
	}

	private CreditTransactionMO getCreditTransactionMO() {
		CreditTransactionMO creditTransactionMO = new CreditTransactionMO();
		creditTransactionMO.setCredit(20000);
		creditTransactionMO.setCredited_dttm("30.12.2020 13:31:16");
		VehicleOwnerMO vehicleOwner = new VehicleOwnerMO();
		vehicleOwner.setId(1);
		creditTransactionMO.setVhOwner(vehicleOwner);
		return creditTransactionMO;
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
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return creditBookMO;

	}

}
