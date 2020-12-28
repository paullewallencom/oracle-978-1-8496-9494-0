package com.packtpub.screencast.coherence.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.packtpub.screencast.coherence.customers.Customer;
import com.packtpub.screencast.coherence.data.CustomerGenerator;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class CacheInsertOverflow {
	static NamedCache cache;

	public static void main(String[] args) throws IOException {

		cache = CacheFactory.getCache("customers");
		Random randomGenerator = new Random();
		long t1, t2;
		CustomerGenerator cg = new CustomerGenerator();
		final int frontCacheSize = 100000;
		final int backCacheSize = 100000;

		ArrayList<Customer> customers = (ArrayList<Customer>) cg
				.getCustomers(frontCacheSize);

		// !!!!!!
		t1 = System.currentTimeMillis();
		putToCache(customers);
		t2 = System.currentTimeMillis();
		System.out.println("Total put time for front tier:" + (t2 - t1)
				+ " ms.");

		customers = (ArrayList<Customer>) cg.getCustomers(backCacheSize,
				frontCacheSize + 1);

		t1 = System.currentTimeMillis();
		putToCache(customers);
		t2 = System.currentTimeMillis();
		System.out
				.println("Total put time for back tier:" + (t2 - t1) + " ms.");

	}

	private static void putToCache(ArrayList<Customer> customers) {
		HashMap<String, Customer> customer = new HashMap<String, Customer>();

		for (int i = 0; i < customers.size(); i++) {
			customer.put(customers.get(i).getId(), customers.get(i));
		}
		cache.putAll(customer);

	}

}
