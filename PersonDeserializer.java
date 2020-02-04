package com.anthem.emep.dckr.microsvc.dataservicexbdf.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class PersonDeserializer extends StdDeserializer<PersonNew> {

	private static final long serialVersionUID = 1L;

	public PersonDeserializer() {
		this(null);
	}

	public PersonDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public PersonNew deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		PersonNew person = new PersonNew();
		JsonNode node = jp.getCodec().readTree(jp);
		person.setCsPersonId(node.get("cspersonid").textValue());
		person.setPrsnMcid(node.get("prsnmcid").textValue());
		//Add other fields
		List<Prsnmemberkey> prsnMemberKeys = new ArrayList<>();
		
		node.get("prsnmemberkeys").forEach(next -> {
			Prsnmemberkey memberKey = new Prsnmemberkey();
			JsonNode member = next.get("prsnmemberkey");
			memberKey.setMbr4pkey(member.get("mbr4pkey").textValue());
			//Add other fields
			prsnMemberKeys.add(memberKey);			 
		 });
		
		person.setPrsnMemberKeys(prsnMemberKeys );
		 
		return person;
	}

}
