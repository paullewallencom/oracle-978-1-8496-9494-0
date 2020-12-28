package com.packtpub.screencast.coherence.cache;

import java.util.Random;
import java.util.Scanner;

import com.packtpub.screencast.coherence.customers.Customer;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class CacheStart {

	public static void main(String[] args) throws InterruptedException {
		NamedCache cache = CacheFactory.getCache("customers");
		System.out.println("Cache size:" + cache.size());
		Random randomGenerator = new Random();
		Customer customer;
		System.out
				.println("To stop cache server type something and press enter:");

		while (true) {

			int custId = randomGenerator.nextInt(cache.size()+1);
			customer = (Customer) cache.get(String.valueOf(custId));

			if (cache.size() != 0) {

				if (customer != null) {
					System.out.println("Customer information of " + custId);
					System.out.println("--------------------");
					System.out.println("Name:" + customer.getFirstName());
					System.out.println("SurName:" + customer.getLastName());
					System.out.println("Country:" + customer.getCountry());
					System.out.println("ZipCode:" + customer.getZipCode());
					System.out.println("Citizen No:" + customer.getCitizenNo());
					System.out.println("Age:" + customer.getAge());
					System.out.println("--------------------");
				} else {
					System.out.println("Does not exist id:" + custId);
				}
			}

			Thread.sleep(1000);
			System.gc();

		}
	}
}
