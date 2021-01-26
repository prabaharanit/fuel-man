package com.mayavan.fuelman.service;

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

@SpringBootTest
class CreditBookServiceImplTest {

	@Autowired
	public CreditBookService creditBookService;

	@Test
	@Transactional
	@Rollback
	public void testCreateCreditBook() {
		try {
			CreditBookMO creditBookMO = getCreditBookMO();
			creditBookService.createCreditBook(creditBookMO);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("get fuel price for dttm for sale");
		}
	}
	
	@Test
	@Transactional
	@Rollback
	public void testUpdateCreditBook() {
		try {
			CreditBookMO creditBookMO = getCreditBookMO();
			creditBookService.updateCreditBook(creditBookMO);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("get fuel price for dttm for sale");
		}
	}
	
	@Ignore
	public void testAllCreditBookEntriesByVhOwner() {
		try {
			creditBookService.getCreditEntriesByVhOwnerId(3);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("get fuel price for dttm for sale");
		}
	}

	@Test
	@Transactional
	@Rollback
	public void testAllPaidCrdBook() {
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
		creditTransactionMO.setCredit(5000);
		creditTransactionMO.setCredited_dttm("30.12.2020 13:31:16");
		VehicleOwnerMO vehicleOwner = new VehicleOwnerMO();
		vehicleOwner.setId(2);
		creditTransactionMO.setVhOwner(vehicleOwner);
		return creditTransactionMO;
	}

	private CreditBookMO getCreditBookMO() {
		CreditBookMO creditBookMO = new CreditBookMO();
		creditBookMO.setAmount_of_sale(27);
		//creditBookMO.setId(159);
		creditBookMO.setLitre_sale_volume(22.2);
		VehicleMO vehicleMO = new VehicleMO();
		vehicleMO.setId(1);
		VehicleOwnerMO vhOwner = new VehicleOwnerMO();
		vhOwner.setId(2);
		vehicleMO.setVhOwner(vhOwner);
		creditBookMO.setVehicleMO(vehicleMO);
		FuelPriceMO fuelPriceMO = new FuelPriceMO();
		FuelTypeMO fuelType = new FuelTypeMO();
		fuelType.setId(2);
		fuelPriceMO.setFuel_type(fuelType);
		fuelPriceMO.setPrice(78);
		creditBookMO.setFuelPriceMO(fuelPriceMO);
		creditBookMO.setComments("test data");
		creditBookMO.setDttm_of_sale("30.12.2020 13:31:16");
		return creditBookMO;

	}

}
