package com.mayavan.fuelman.repo.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "daily_fuel_price")
@Table(name = "daily_fuel_price")
public class FuelPrice {
	private int id;
	private int fuel_type_id;
	private double price;
	private Date date_of_sale;
	private Timestamp from_dttm;
	private Timestamp to_dttm;
	private int is_deleted;
	private Timestamp created_dttm;
	private Timestamp modified_dttm;

	public FuelPrice() {

	}

	/*
	 * @Query("SELECT id, fuel_type_id, price, date_of_sale, is_deleted, created_dttm, modified_dttm, from_dttm, to_dttm\r\n"
	 * + "	FROM daily_fuel_price where from_dttm <= ?1 and to_dttm >= ?1")
	 */
	public FuelPrice(int id, int fuel_type_id, double price, Date date_of_sale,int is_deleted, Timestamp created_dttm, Timestamp modified_dttm, Timestamp from_dttm, Timestamp to_dttm) {
		this.id = id;
		this.price = price;
		this.fuel_type_id = fuel_type_id;
		this.date_of_sale = date_of_sale;
		this.created_dttm = created_dttm;
		this.modified_dttm = modified_dttm;
		this.is_deleted = is_deleted;
		this.from_dttm = from_dttm;
		this.to_dttm = to_dttm;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "fuel_type_id", nullable = false)
	public int getFuel_type_id() {
		return fuel_type_id;
	}

	public void setFuel_type_id(int fuel_type_id) {
		this.fuel_type_id = fuel_type_id;
	}

	@Column(name = "price", nullable = false)
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	@Column(name = "date_of_sale", nullable = false)
	public Date getDate_of_sale() {
		return date_of_sale;
	}

	public void setDate_of_sale(Date date_of_sale) {
		this.date_of_sale = date_of_sale;
	}

	@Column(name = "is_deleted", nullable = true)
	public int getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}

	@Column(name = "created_dttm", nullable = true)
	public Timestamp getCreated_dttm() {
		return created_dttm;
	}

	public void setCreated_dttm(Timestamp created_dttm) {
		this.created_dttm = created_dttm;
	}

	@Column(name = "modified_dttm", nullable = true)
	public Timestamp getModified_dttm() {
		return modified_dttm;
	}

	public void setModified_dttm(Timestamp modified_dttm) {
		this.modified_dttm = modified_dttm;
	}

	@Column(name = "from_dttm", nullable = true)
	public Timestamp getFrom_dttm() {
		return from_dttm;
	}

	public void setFrom_dttm(Timestamp from_dttm) {
		this.from_dttm = from_dttm;
	}

	@Column(name = "to_dttm", nullable = true)
	public Timestamp getTo_dttm() {
		return to_dttm;
	}

	public void setTo_dttm(Timestamp to_dttm) {
		this.to_dttm = to_dttm;
	}

}
