package com.packtpub.screencast.coherence.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.packtpub.screencast.coherence.customers.Customer;

public class CustomerGenerator {

	Random randomGenerator = new Random();

	public String getRandomFirstName() {
		String[] names = { "Ahmet", "Mehmet", "John", "Tony", "Tom", "James",
				"Robert", "David", "William", "Thomas", "Joseph", "Muhammed",
				"Mark", "Donald", "Brian", "Steven", "Kevin", "Anthony", "Joe",
				"Jason", "Stephen", "Andrew", "Patrick", "Peter", "Susan",
				"Mary", "Linda", "Barbara", "Elizabeth", "Lisa", "Nancy",
				"Karen", "Betty", "Helen", "Susan", "Maria", "Jennifer",
				"Laura", "Michelle", "Nicola", "Emy", "Andrea", "Rick",
				"Shane", "Kate" };

		return getRandomString(names);
	}

	public String getRandomLastName() {
		String[] lastNames = { "Brown", "White", "Foster", "Smith", "Johnson",
				"Williams", "Jones", "Miller", "Wilson", "Martin", "Jackson",
				"Garcia", "Martinez", "Gonzalez", "Garcia", "Martin",
				"Cabrera", "Tomic", "Kovac", "Andersen", "Pedersen", "Larsen",
				"Nielsen", "Bernard", "David" };
		return getRandomString(lastNames);
	}

	public String getRandomCountry() {
		String[] countries = { "England", "Italy", "Turkey", "France", "Spain",
				"Japan", "Greece", "Germany", "USA" };
		return getRandomString(countries);
	}

	public int getRandomZipCode() {
		return randomGenerator.nextInt(50000);
	}

	public int getRandomAge() {
		return randomGenerator.nextInt(70) + 20;
	}

	public String getRandomCitizenNo() {
		return Integer.toString(randomGenerator.nextInt(89999) + 10000)
				+ Integer.toString(randomGenerator.nextInt(89999) + 10000);
	}

	private String getRandomString(String[] strings) {
		// TODO Auto-generated method stub
		return strings[randomGenerator.nextInt(strings.length)];
	}

	public List<Customer> getCustomers(int customersSize) {
		List<Customer> customers = new ArrayList<Customer>();

		for (int i = 0; i < customersSize; i++) {
			Customer customer = new Customer(Integer.toString(i),
					getRandomFirstName(), getRandomLastName(),
					getRandomCountry(), getRandomZipCode(),
					getRandomCitizenNo(), getRandomAge());
			customers.add(customer);
		}

		return customers;

	}
	
	
}
