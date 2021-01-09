package com.mayavan.fuelman.repo.model;

public class FuelPriceMO{
	private int id;
	private FuelTypeMO fuel_type;
	private double price;
	private String date_of_sale;
	private String from_dttm;
	private String to_dttm;
	private int is_deleted;
	private String created_dttm;
	private String modified_dttm;
	
	public FuelPriceMO() {

	}

	public FuelPriceMO(int id, FuelTypeMO fuel_type, double price, String date_of_sale, String created_dttm,
			String modified_dttm, int is_deleted, String from_dttm, String to_dttm) {
		this.id = id;
		this.price = price;
		this.fuel_type = fuel_type;
		this.date_of_sale = date_of_sale;
		this.created_dttm = created_dttm;
		this.modified_dttm = modified_dttm;
		this.is_deleted = is_deleted;
		this.from_dttm = from_dttm;
		this.to_dttm = to_dttm;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public FuelTypeMO getFuel_type() {
		return fuel_type;
	}

	public void setFuel_type(FuelTypeMO fuel_type) {
		this.fuel_type = fuel_type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDate_of_sale() {
		return date_of_sale;
	}

	public void setDate_of_sale(String date_of_sale) {
		this.date_of_sale = date_of_sale;
	}

	public int getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}

	public String getCreated_dttm() {
		return created_dttm;
	}

	public void setCreated_dttm(String created_dttm) {
		if(created_dttm != null)
		this.created_dttm = created_dttm.toString();
	}

	public String getModified_dttm() {
		return modified_dttm;
	}

	public void setModified_dttm(String modified_dttm) {
		if(modified_dttm != null)
		this.modified_dttm = modified_dttm.toString();
	}

	public String getFrom_dttm() {
		return from_dttm;
	}

	public void setFrom_dttm(String from_dttm) {
		this.from_dttm = from_dttm;
	}

	public String getTo_dttm() {
		return to_dttm;
	}

	public void setTo_dttm(String to_dttm) {
		this.to_dttm = to_dttm;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ("id =" +this.id + " fuel price " + this.price +  " fuel type id "+ this.getFuel_type().getId() + " from dttm " + this.from_dttm + " to dttm"+ this.to_dttm);
	}
	/*
	 * @Override public int compareTo(FuelPriceMO compareWith) { return
	 * this.date_of_sale - compareWith.date_of_sale; }
	 */

}
