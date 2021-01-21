package com.mayavan.fuelman.repo.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class VehicleMO {

	private int id;
	private VehicleType vhType;
	private VehicleOwnerMO vhOwner;
	private String numberPlate;
	private String created_dttm;
	private String modified_dttm;
	private int is_deleted;

	public VehicleMO() {

	}

	public VehicleMO(VehicleType vhType, VehicleOwnerMO vhOwner, String numberPlate, String created_dttm, String modified_dttm, int is_deleted) {
		this.vhType = vhType;
		this.vhOwner = vhOwner;
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

	public VehicleType getVhType() {
		return vhType;
	}

	public void setVhType(VehicleType vhType) {
		this.vhType = vhType;
	}

	public VehicleOwnerMO getVhOwner() {
		return vhOwner;
	}

	public void setVhOwner(VehicleOwnerMO vhOwner) {
		this.vhOwner = vhOwner;
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
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

	public int getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}

	@Override
	public String toString() {
		return "FuelType [id=" + id + ", vh type id=" + vhType.getId() + ", vh owner id =" + vhOwner.getId() + ", creaded_dttm =" + created_dttm
				+ ", modified_dttm =" + modified_dttm + ", is_deleted =" + is_deleted + "]";
	}
}
