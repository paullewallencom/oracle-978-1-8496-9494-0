package com.packtpub.screencast.coherence.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import com.packtpub.screencast.coherence.customers.Customer;
import com.packtpub.screencast.coherence.data.CustomerGenerator;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.Member;
import com.tangosol.net.NamedCache;

public class CacheWithoutEntryProcessor {
	private static int customerId;
	private static NamedCache cache;

	public static void main(String[] args) throws IOException,
			InterruptedException {
		
		final int customerId = 5;
		final String customerIdStr = String.valueOf(customerId);

		Customer customer = null;
		cache = CacheFactory.getCache("customers");

		// generates customers
		CustomerGenerator cg = new CustomerGenerator();
		ArrayList<Customer> customers = (ArrayList<Customer>) cg
				.getCustomers(20000);
		putToCache(customers);

		// prints the cache size
		System.out.println("Cache size:" + cache.size());

		
		// get the customer
		customer = (Customer) cache.get(customerIdStr);
		if (customer != null) {
			printOutCustomer(customer);
		}
		
		// update it
		if (cache.lock(customerIdStr)) {
			try {
				// we should get again the customer because it may be changed by
				// another node
				customer = (Customer) cache.get(customerIdStr);
				customer.setAge(customer.getAge() + 1);
				cache.put(customerIdStr, customer);
			} finally {
				cache.unlock(customerIdStr);
			}
		} else {
			System.out.println("Lock is not acquired!");
		}

		// print the customer again
		printOutCustomer(customer);

	}

	/**
	 * @param customer
	 */
	private static void printOutCustomer(Customer customer) {
		System.out.println("Customer information");
		System.out.println("--------------------");
		System.out.println("Name:" + customer.getFirstName());
		System.out.println("SurName:" + customer.getLastName());
		System.out.println("Country:" + customer.getCountry());
		System.out.println("ZipCode:" + customer.getZipCode());
		System.out.println("Citizen No:" + customer.getCitizenNo());
		System.out.println("Age:" + customer.getAge());
		System.out.println("--------------------");
	}

	private static void putToCache(ArrayList<Customer> customers) {

		for (int i = 0; i < customers.size(); i++) {
			cache.put(customers.get(i).getId(), customers.get(i));
		}

	}
}