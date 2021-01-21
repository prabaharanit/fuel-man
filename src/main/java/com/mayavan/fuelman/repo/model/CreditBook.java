package com.mayavan.fuelman.repo.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "credit_book")
@Table(name = "credit_book")
public class CreditBook {

	private int id;
	private int vh_id;
	private int fuel_type_id;
	private double litre_sale_volume;
	private double amount_of_sale;
	private Integer credit_transaction_id;
	private double fuelPrice;
	private Timestamp dttm_of_sale;
	private int is_deleted;
	private int is_paid;
	private Timestamp created_dttm;
	private Timestamp modified_dttm;
	private String comments;

	public CreditBook() {
	}

	public CreditBook(int vh_id, int fuel_type_id, double litre_sale_volumne, double amount_of_sale, int is_deleted,
			int is_paid, Timestamp dttm_of_sale, Timestamp created_dttm, Timestamp modified_dttm,
			int credit_transaction_id) {
		this.vh_id = vh_id;
		this.fuel_type_id = fuel_type_id;
		this.litre_sale_volume = litre_sale_volumne;
		this.amount_of_sale = amount_of_sale;
		this.dttm_of_sale = dttm_of_sale;
		this.credit_transaction_id = credit_transaction_id;
		this.is_paid = is_paid;
		this.is_deleted = is_deleted;
		this.created_dttm = created_dttm;
		this.modified_dttm = modified_dttm;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "vh_id", nullable = false)
	public int getVehicleId() {
		return vh_id;
	}

	public void setVehicleId(int vh_id) {
		this.vh_id = vh_id;
	}

	@Column(name = "fuel_type_id", nullable = false)
	public int getFuel_type_id() {
		return fuel_type_id;
	}

	public void setFuel_type_id(int fuel_type_id) {
		this.fuel_type_id = fuel_type_id;
	}

	@Column(name = "litre_sale_volume", nullable = false)
	public double getLitre_sale_volume() {
		return litre_sale_volume;
	}

	public void setLitre_sale_volume(double litre_sale_volume) {
		this.litre_sale_volume = litre_sale_volume;
	}

	@Column(name = "amount_of_sale", nullable = false)
	public double getAmount_of_sale() {
		return amount_of_sale;
	}

	public void setAmount_of_sale(double amount_of_sale) {
		this.amount_of_sale = amount_of_sale;
	}

	@Column(name = "fuel_sale_price", nullable = false)
	public double getFuelPrice() {
		return fuelPrice;
	}

	public void setFuelPrice(double fuelPrice) {
		this.fuelPrice = fuelPrice;
	}

	@Column(name = "dttm_of_sale", nullable = false)
	public Timestamp getDttm_of_sale() {
		return dttm_of_sale;
	}

	public void setDttm_of_sale(Timestamp dttm_of_sale) {
		this.dttm_of_sale = dttm_of_sale;
	}
	
	@Column(name = "credit_transaction_id", nullable = true)
	public Integer getCredit_transaction_id() {
		return credit_transaction_id;
	}

	public void setCredit_transaction_id(Integer credit_transaction_id) {
		this.credit_transaction_id = credit_transaction_id;
	}

	@Column(name = "is_deleted", nullable = true)
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

	@Column(name = "comments", nullable = true)
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
