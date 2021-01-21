package com.mayavan.fuelman.repo.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "vehicle")
@Table(name = "vehicle")
public class Vehicle {

	private int id;
	private int vhTypeId;
	private int vhOwnerId;
	private String numberPlate;
	private Timestamp created_dttm;
	private Timestamp modified_dttm;
	private int is_deleted;

	public Vehicle() {

	}

	public Vehicle(int vhTypeId, int vhOwnerId, String numberPlate, Timestamp created_dttm, Timestamp modified_dttm, int is_deleted) {
		this.vhTypeId = vhTypeId;
		this.vhOwnerId = vhOwnerId;
		this.numberPlate = numberPlate;
		this.created_dttm = created_dttm;
		this.modified_dttm = modified_dttm;
		this.is_deleted = is_deleted;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "vh_type_id", nullable = false)
	public int getVhTypeId() {
		return vhTypeId;
	}

	public void setVhTypeId(int vhTypeId) {
		this.vhTypeId = vhTypeId;
	}

	@Column(name = "vh_owner_id", nullable = false)
	public int getVhOwnerId() {
		return vhOwnerId;
	}

	public void setVhOwnerId(int vhOwnerId) {
		this.vhOwnerId = vhOwnerId;
	}

	@Column(name = "number_plate", nullable = false)
	public String getNumberPlate() {
		return numberPlate;
	}

	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
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

	@Column(name = "is_deleted", nullable = true)
	public int getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}

	@Override
	public String toString() {
		return "FuelType [id=" + id + ", vh type id=" + vhTypeId + ", vh owner id =" + vhOwnerId + ", creaded_dttm =" + created_dttm
				+ ", modified_dttm =" + modified_dttm + ", is_deleted =" + is_deleted + "]";
	}
}
