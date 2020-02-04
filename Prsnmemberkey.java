package com.anthem.emep.dckr.microsvc.dataservicexbdf.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Prsnmemberkey {

	private String mbr4pkey;
	private String mbrhcid;
	private String mbrhashkey;
	private String mbrsubscriber;
	private String mbrehubid;
	private String mbrdgtlenrlmntrel;

	public String getMbr4pkey() {
		return mbr4pkey;
	}

	public void setMbr4pkey(String mbr4pkey) {
		this.mbr4pkey = mbr4pkey;
	}

	public String getMbrhcid() {
		return mbrhcid;
	}

	public void setMbrhcid(String mbrhcid) {
		this.mbrhcid = mbrhcid;
	}

	public String getMbrhashkey() {
		return mbrhashkey;
	}

	public void setMbrhashkey(String mbrhashkey) {
		this.mbrhashkey = mbrhashkey;
	}

	public String getMbrsubscriber() {
		return mbrsubscriber;
	}

	public void setMbrsubscriber(String mbrsubscriber) {
		this.mbrsubscriber = mbrsubscriber;
	}

	public String getMbrehubid() {
		return mbrehubid;
	}

	public void setMbrehubid(String mbrehubid) {
		this.mbrehubid = mbrehubid;
	}

	public String getMbrdgtlenrlmntrel() {
		return mbrdgtlenrlmntrel;
	}

	public void setMbrdgtlenrlmntrel(String mbrdgtlenrlmntrel) {
		this.mbrdgtlenrlmntrel = mbrdgtlenrlmntrel;
	}

	@Override
	public String toString() {
		return "Prsnmemberkey [mbr4pkey=" + mbr4pkey + ", mbrhcid=" + mbrhcid + ", mbrhashkey=" + mbrhashkey
				+ ", mbrsubscriber=" + mbrsubscriber + ", mbrehubid=" + mbrehubid + ", mbrdgtlenrlmntrel="
				+ mbrdgtlenrlmntrel + "]";
	}
	
	

}
