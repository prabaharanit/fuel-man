package com.mayavan.fuelman.repo.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle_owner")
public class VehicleOwner {

	private int id;
	private String name;
	private String mobile_no;
	private String transport_name;
	private Timestamp created_dttm;
	private Timestamp modified_dttm;
	private int is_deleted;

	public VehicleOwner() {

	}

	public VehicleOwner(String name, String mobile_no, String transport_name, Timestamp created_dttm,
			Timestamp modified_dttm, int is_deleted) {
		this.mobile_no = mobile_no;
		this.transport_name = transport_name;
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

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "mobile_no", nullable = false)
	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	@Column(name = "transport_name", nullable = false)
	public String getTransport_name() {
		return transport_name;
	}

	public void setTransport_name(String transport_name) {
		this.transport_name = transport_name;
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
		return "VehicleOwner [id=" + id + ", name=" + name + ", mobile_no=" + mobile_no + ", transport name="
				+ transport_name + ", creaded_dttm =" + created_dttm + ", modified_dttm =" + modified_dttm
				+ ", is_deleted =" + is_deleted + "]";
	}
}
