package com.mayavan.fuelman.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class FuelPriceServiceImplTest {
	
	@Autowired
	private FuelService fuelService;

	@Test
	public void testGetFuelPriceForDttmOfSale() {
		try {
			fuelService.getFuelPriceByDateTime("30 12 2020 13:31:16", 2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("get fuel price for dttm for sale");
		}
	}
	
}
