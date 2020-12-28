package com.packtpub.screencast.coherence.customers;

import java.io.IOException;
import java.io.Serializable;

import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;

public class Customer implements PortableObject {

	private String id;
	private String firstName;
	private String lastName;
	private String country;
	private int zipCode;
	private String citizenNo;
	private int age;
	private String[] siblingNames;

	private final int ID = 0;
	private final int FIRST_NAME = 1;
	private final int LAST_NAME = 2;
	private final int COUNTRY = 3;
	private final int ZIP_CODE = 4;
	private final int CITIZEN_NO = 5; 
	private final int AGE = 6;
	private final int SIBLINGNAMES = 7;
	/**
	 * 
	 */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getCitizenNo() {
		return citizenNo;
	}

	public void setCitizenNo(String citizenNo) {
		this.citizenNo = citizenNo;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String[] getSiblingNames() {
		return siblingNames;
	}

	public void setSiblingNames(String[] sibLingNames) {
		this.siblingNames = sibLingNames;
	}

	public Customer() {
		//
	}

	public Customer(String id, String firstName, String lastName,
			String country, int zipCode, String citizenNo, int age, String[] siblingNames) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.zipCode = zipCode;
		this.citizenNo = citizenNo;
		this.age = age;
		this.siblingNames = siblingNames;
	}

	@Override
	public void readExternal(PofReader reader) throws IOException {
		// Reads Customer object from the serialized form
		id = reader.readString(ID);
		firstName = reader.readString(FIRST_NAME);
		lastName = reader.readString(LAST_NAME);
		country = reader.readString(COUNTRY);
		zipCode = reader.readInt(ZIP_CODE);
		citizenNo = reader.readString(CITIZEN_NO);
		age = reader.readInt(AGE);
		siblingNames = (String[]) reader.readObjectArray(SIBLINGNAMES, new String[0]);
		System.out.println("Read External Method...");

				
	}

	@Override
	public void writeExternal(PofWriter writer) throws IOException {
		// Writes Customer object into the serialized form
		
		writer.writeString(ID, id);
		writer.writeString(FIRST_NAME, firstName);
		writer.writeString(LAST_NAME, lastName);
		writer.writeString(COUNTRY, country);
		writer.writeInt(ZIP_CODE, zipCode);
		writer.writeString(CITIZEN_NO, citizenNo);
		writer.writeInt(AGE, age);
		writer.writeObjectArray(SIBLINGNAMES, siblingNames);
		System.out.println("Write External Method...");
	}

}
