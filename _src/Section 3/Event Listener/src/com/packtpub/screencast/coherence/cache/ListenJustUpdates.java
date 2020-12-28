package com.packtpub.screencast.coherence.cache;

import java.io.IOException;
import java.util.Scanner;

import com.packtpub.screencast.coherence.customers.Customer;
import com.packtpub.screencast.coherence.data.CustomerGenerator;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class ListenJustUpdates {
	private static int customerId;

	public static void main(String[] args) throws IOException,
			InterruptedException {
		NamedCache cache = CacheFactory.getCache("customers");
		System.out.println("Cache size:" + cache.size());

		cache.addMapListener(new CustomerEventListener(), new UpdateFilter(),
				false);

		String dummy;
		Scanner user_input = new Scanner(System.in);
		Customer customer;
		CustomerGenerator cg = new CustomerGenerator();

		while (true) {

			System.out
					.println("To put an object type P; get an object type G and press enter:");
			dummy = user_input.next();

			if (dummy.equalsIgnoreCase("P")) {
				System.out.println("Enter an ID for random customer(0-1000):");
				dummy = user_input.next();
				customerId = Integer.parseInt(dummy);
				cache.put(dummy, cg.getCustomer(customerId));
			} else {
				System.out.println("Enter an ID:");
				dummy = user_input.next();
				customer = (Customer) cache.get(dummy);

				if (customer != null) {
					printOutCustomer(customer);
				} else {

					System.out.println("Invalid id:" + dummy);
				}
			}

		}

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
}