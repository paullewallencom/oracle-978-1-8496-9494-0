package com.packtpub.screencast.coherence.cache;

import java.io.IOException;
import java.util.Scanner;

import com.packtpub.screencast.coherence.customers.Customer;
import com.packtpub.screencast.coherence.data.CustomerGenerator;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.Filter;
import com.tangosol.util.filter.EqualsFilter;
import com.tangosol.util.filter.MapEventFilter;

public class ListenFilter {
	private static int customerId;

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException {
		NamedCache cache = CacheFactory.getCache("customers");
		System.out.println("Cache size:" + cache.size());

		Filter eventFilter = new EqualsFilter("getId", String.valueOf(100));
		cache.addMapListener(new CustomerEventListener(), new MapEventFilter(
				MapEventFilter.E_ALL, eventFilter), false);
//		cache.addMapListener(new CustomerEventListener(), eventFilter, false);
		
		
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