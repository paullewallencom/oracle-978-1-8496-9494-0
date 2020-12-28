package com.packtpub.screencast.coherence.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import com.packtpub.screencast.coherence.customers.Customer;
import com.packtpub.screencast.coherence.data.CustomerGenerator;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.Filter;
import com.tangosol.util.aggregator.BigDecimalMax;
import com.tangosol.util.aggregator.DoubleMax;
import com.tangosol.util.extractor.ReflectionExtractor;
import com.tangosol.util.filter.AndFilter;
import com.tangosol.util.filter.EqualsFilter;
import com.tangosol.util.filter.GreaterFilter;
import com.tangosol.util.filter.InFilter;

public class CacheQueryIndex {

	public static void main(String[] args) {
		// variable defining
		String input;
		int counter = 0;
		Customer customer = null;
		Set<String> countrySet = new HashSet<String>();
		NamedCache cache = CacheFactory.getCache("customers");
		long t1, t2;

		Scanner user_input = new Scanner(System.in);

		// fills the cache
		System.out.println("Cache is being loaded...");
		fillTheCache(cache);
		
		System.out.println("Cache size:" + cache.size());

		// countrySet will be used for country filtering
		countrySet.add("Italy");
		countrySet.add("Japan");
		countrySet.add("England");

		// form the filter
		Filter customerFilter = new InFilter("getCountry", countrySet);

		Set<Map.Entry> filteredData;
		
		// adding index
		cache.addIndex(new ReflectionExtractor("getCountry"), true, null);
		
		System.out.println("In order to start the query with index, type something:");
		input  = user_input.next();
		
		// filters the coherence cache
		t1 = System.currentTimeMillis();
		filteredData = cache.entrySet(customerFilter);
		t2 = System.currentTimeMillis();
		System.out.println("With Index:" + (t2 - t1) + " ms.");

		cache.removeIndex(new ReflectionExtractor("getCountry"));
		filteredData = null;
		
		System.out.println("In order to start the query withOUT index, type something:");
		input  = user_input.next();
		
		// filters the coherence cache
		t1 = System.currentTimeMillis();
		filteredData = cache.entrySet(customerFilter);
		t2 = System.currentTimeMillis();
		System.out.println("Without Index:" + (t2 - t1) + " ms.");

		


	}

	/**
	 * @param cache
	 */
	private static void fillTheCache(NamedCache cache) {
		CustomerGenerator cg = new CustomerGenerator();
		List<Customer> customers = cg.getCustomers(500000);
		
		Map<String,Customer> buffer = new HashMap<String,Customer>();
		for (int i=0; i<customers.size(); i++)
		{
			buffer.put(customers.get(i).getId(), customers.get(i));
		}
		cache.putAll(buffer);
	}

}
