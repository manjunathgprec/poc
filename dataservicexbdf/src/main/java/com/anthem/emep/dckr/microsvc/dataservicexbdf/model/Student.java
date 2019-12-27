package com.anthem.emep.dckr.microsvc.dataservicexbdf.model;

import java.sql.Date;

public class Student extends BaseVO {

	private long id;

	private String name;

	private String code;

	private Date dob;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", code=" + code + ", dob=" + dob + "]";
	}
	
	
	

}
