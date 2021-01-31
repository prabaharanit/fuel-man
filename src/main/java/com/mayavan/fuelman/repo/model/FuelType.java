package com.mayavan.fuelman.repo.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "fuel_type")
@Table(name = "fuel_type")
public class FuelType {

	private int id;
	private String type;
	private String name;
	private Timestamp created_dttm;
	private Timestamp modified_dttm;
	private int is_deleted;

	public FuelType() {

	}

	public FuelType(int id, String type, String name, Timestamp created_dttm, Timestamp modified_dttm, int is_deleted) {
		this.id = id;
		this.type = type;
		this.name = name;
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

	@Column(name = "type", nullable = false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return "FuelType [id=" + id + ", name=" + name + ", type=" + type + ", creaded_dttm =" + created_dttm
				+ ", modified_dttm =" + modified_dttm + ", is_deleted =" + is_deleted + "]";
	}
}
