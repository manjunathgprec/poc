package com.anthem.emep.dckr.microsvc.dataservicexbdf.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = PersonDeserializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonNew {
	
//	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
//		
//		String json = "{\"cspersonid\":\"CSUTE16K29CPN9A0L\",\"prsnehubprsnid\":277,\"prsnmcid\":\"1099805834\",\"prsnreclstupdtdtm\":\"2019-04-22 16:09:13\",\"prsnmemberkeys\":[{\"prsnmemberkey\":{\"mbr4pkey\":\"808-186110-IN0900589-1\",\"mbrhcid\":\"104680352299\",\"mbrhashkey\":\"F989BE4A2CF39A7663E0C7BA4AF4FA33\",\"mbrsubscriber\":\"IN0900589\",\"mbrehubid\":\"IN0900589001\",\"mbrdgtlenrlmntrel\":null}}]}";
//		PersonNew obj = new  PersonNew();
//		ObjectMapper objectMapper = new ObjectMapper();
//		PersonNew readValue = objectMapper.readValue(json, PersonNew.class);
//		System.out.println(readValue);
//		
//		String writeValueAsString = new ObjectMapper().writeValueAsString(readValue);
//		System.out.println(writeValueAsString);
//	}
	
	private String csPersonId;

	private Integer prsnEhubPrsnId;

	private String prsnMcid;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date prsnRecLstUpdtDtm;

	private List<Prsnmemberkey> prsnMemberKeys;

	public String getCsPersonId() {
	return csPersonId;
	}

	public void setCsPersonId(String csPersonId) {
	this.csPersonId = csPersonId;
	}

	public Integer getPrsnEhubPrsnId() {
	return prsnEhubPrsnId;
	}

	public void setPrsnEhubPrsnId(Integer prsnEhubPrsnId) {
	this.prsnEhubPrsnId = prsnEhubPrsnId;
	}

	public String getPrsnMcid() {
	return prsnMcid;
	}

	public void setPrsnMcid(String prsnMcid) {
	this.prsnMcid = prsnMcid;
	}

	public Date getPrsnRecLstUpdtDtm() {
	return prsnRecLstUpdtDtm;
	}

	public void setPrsnRecLstUpdtDtm(Date prsnRecLstUpdtDtm) {
	this.prsnRecLstUpdtDtm = prsnRecLstUpdtDtm;
	}

	public List<Prsnmemberkey> getPrsnMemberKeys() {
	return prsnMemberKeys;
	}

	public void setPrsnMemberKeys(List<Prsnmemberkey> prsnMemberKeys) {
	this.prsnMemberKeys = prsnMemberKeys;
	}

//	@Override
//	public String toString() {
//		return "PersonNew [csPersonId=" + csPersonId + ", prsnEhubPrsnId=" + prsnEhubPrsnId + ", prsnMcid=" + prsnMcid
//				+ ", prsnRecLstUpdtDtm=" + prsnRecLstUpdtDtm + ", prsnMemberKeys=" + prsnMemberKeys + "]";
//	}
	
	


}
