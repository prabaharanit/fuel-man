package com.mayavan.fuelman.repo.model;

import javax.persistence.Column;

public class CreditBookMO {

	private int id;
	private VehicleMO vehicleMO;
	private FuelPriceMO fuelPriceMO;
	private double litre_sale_volume;
	private double amount_of_sale;
	private String dttm_of_sale;
	private int credit_transaction_id;
	private int is_deleted;
	private int is_paid = 0;
	private String created_dttm;
	private String modified_dttm;
	private String comments;

	public CreditBookMO() {
	}

	public CreditBookMO(VehicleMO vehicleMO, FuelPriceMO fuelPriceMO, double litre_sale_volumne, double amount_of_sale,
			int is_deleted, int is_paid, String dttm_of_sale, String created_dttm, String modified_dttm,
			int credit_transaction_id) {
		this.vehicleMO = vehicleMO;
		this.fuelPriceMO = fuelPriceMO;
		this.litre_sale_volume = litre_sale_volumne;
		this.amount_of_sale = amount_of_sale;
		this.dttm_of_sale = dttm_of_sale;
		this.credit_transaction_id = credit_transaction_id;
		this.is_paid = is_paid;
		this.is_deleted = is_deleted;
		this.created_dttm = created_dttm;
		this.modified_dttm = modified_dttm;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public VehicleMO getVehicleMO() {
		return vehicleMO;
	}

	public void setVehicleMO(VehicleMO vehicleMO) {
		this.vehicleMO = vehicleMO;
	}

	public FuelPriceMO getFuelPriceMO() {
		return fuelPriceMO;
	}

	public void setFuelPriceMO(FuelPriceMO fuelPriceMO) {
		this.fuelPriceMO = fuelPriceMO;
	}

	public double getLitre_sale_volume() {
		return litre_sale_volume;
	}

	public void setLitre_sale_volume(double litre_sale_volume) {
		this.litre_sale_volume = litre_sale_volume;
	}

	public double getAmount_of_sale() {
		return amount_of_sale;
	}

	public void setAmount_of_sale(double amount_of_sale) {
		this.amount_of_sale = amount_of_sale;
	}

	public String getDttm_of_sale() {
		return dttm_of_sale;
	}

	public void setDttm_of_sale(String dttm_of_sale) {
		this.dttm_of_sale = dttm_of_sale;
	}

	public int getCredit_transaction_id() {
		return credit_transaction_id;
	}

	public void setCredit_transaction_id(int credit_transaction_id) {
		this.credit_transaction_id = credit_transaction_id;
	}

	public int getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}

	@Column(name = "is_paid", nullable = true)
	public int getIs_paid() {
		return is_paid;
	}

	public void setIs_paid(int is_paid) {
		this.is_paid = is_paid;
	}

	public String getCreated_dttm() {
		return created_dttm;
	}

	public void setCreated_dttm(String created_dttm) {
		this.created_dttm = created_dttm;
	}

	public String getModified_dttm() {
		return modified_dttm;
	}

	public void setModified_dttm(String modified_dttm) {
		this.modified_dttm = modified_dttm;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
