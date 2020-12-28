package com.packtpub.screencast.coherence.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.packtpub.screencast.coherence.customers.Customer;
import com.packtpub.screencast.coherence.data.CustomerGenerator;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class CacheInsert {
	static NamedCache cache;

	public static void main(String[] args) throws IOException {
		cache = CacheFactory.getCache("customers");
		Random randomGenerator = new Random();

		CustomerGenerator cg = new CustomerGenerator();
		ArrayList<Customer> customers = (ArrayList<Customer>) cg
				.getCustomers(2000);
		putToCache(customers);

		while (true) {

			// it puts the object into the cache
			// System.out.println("Customer name:"+fields[0]+"-"+fields[1]);
			customers = (ArrayList<Customer>) cg.getCustomers(randomGenerator
					.nextInt(5),cache.size());
			putToCache(customers);
			System.out.println("Cache size:" + cache.size());

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static void putToCache(ArrayList<Customer> customers) {

		for (int i = 0; i < customers.size(); i++) {
			cache.put(customers.get(i).getId(), customers.get(i));
		}

	}

}
