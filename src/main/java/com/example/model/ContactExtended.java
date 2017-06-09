package com.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactExtended extends Person {
	private Person person;
	private Map<String, String> additionnalData = new HashMap<String, String>();

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Map<String, String> getAdditionnalData() {
		return additionnalData;
	}

	public void setAdditionnalData(Map<String, String> additionnalData) {
		this.additionnalData = additionnalData;
	}

}
