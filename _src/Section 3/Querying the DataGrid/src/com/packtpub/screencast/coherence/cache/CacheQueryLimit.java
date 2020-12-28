package com.packtpub.screencast.coherence.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Map;
import com.packtpub.screencast.coherence.customers.Customer;
import com.packtpub.screencast.coherence.data.CustomerGenerator;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.Filter;
import com.tangosol.util.extractor.ReflectionExtractor;
import com.tangosol.util.filter.AlwaysFilter;
import com.tangosol.util.filter.AndFilter;
import com.tangosol.util.filter.GreaterFilter;
import com.tangosol.util.filter.InFilter;
import com.tangosol.util.filter.LimitFilter;
import com.tangosol.util.filter.NotEqualsFilter;
import com.tangosol.util.filter.OrFilter;

public class CacheQueryLimit {

	public static void main(String[] args) {
		// variable defining
		String input;
		Customer customer = null;
		Set<String> countrySet = new HashSet<String>();
		NamedCache cache = CacheFactory.getCache("customers");

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
		LimitFilter customerFilter = new LimitFilter(new AndFilter(
				new GreaterFilter(new ReflectionExtractor("getId"),
						String.valueOf(1000)), new InFilter("getCountry",
						countrySet)), 10);

		// filters and sort the filtered data
		Set<Map.Entry> sortedAndFilteredAndLimitedData = cache.entrySet(
				customerFilter, null);

		// prints out the customers whose key value is more than 1000 and
		// country is in the countrySet set
		// and sorts by id
		System.out.println("Top 10 records:");
		System.out.println("===============");
		printCustomers(sortedAndFilteredAndLimitedData);

		
		customerFilter.nextPage();
		sortedAndFilteredAndLimitedData = cache.entrySet(customerFilter, null);
		System.out.println("10th-20th records:");
		System.out.println("===============");
		printCustomers(sortedAndFilteredAndLimitedData);
		
		customerFilter.setPage(6);
		sortedAndFilteredAndLimitedData = cache.entrySet(customerFilter, null);
		System.out.println("60th-70th records:");
		System.out.println("===============");
		printCustomers(sortedAndFilteredAndLimitedData);
		

	}

	/**
	 * @param counter
	 * @param sortedAndFilteredAndLimitedData
	 */
	private static void printCustomers(
			Set<Map.Entry> sortedAndFilteredAndLimitedData) {
		int counter = 0;

		Customer customer;

		// filtered and sorted and limited set returned to the Iterator then
		// printed
		Iterator<Map.Entry> filteredDataIterator = sortedAndFilteredAndLimitedData
				.iterator();
		while (filteredDataIterator.hasNext()) {

			Map.Entry entry = filteredDataIterator.next();

			customer = (Customer) entry.getValue();
			System.out.println("Customer information for #" + counter++);
			System.out.println("--------------------");
			System.out.println("Id:" + customer.getId());
			System.out.println("Name:" + customer.getFirstName());
			System.out.println("SurName:" + customer.getLastName());
			System.out.println("Country:" + customer.getCountry());
			System.out.println("ZipCode:" + customer.getZipCode());
			System.out.println("Citizen No:" + customer.getCitizenNo());
			System.out.println("Age:" + customer.getAge());
			System.out.println("--------------------");

			counter++;
		}
	}

	private static void fillTheCache(NamedCache cache) {
		CustomerGenerator cg = new CustomerGenerator();
		List<Customer> customers = cg.getCustomers(10000);

		Map<String, Customer> buffer = new HashMap<String, Customer>();
		for (int i = 0; i < customers.size(); i++) {
			buffer.put(customers.get(i).getId(), customers.get(i));
		}
		cache.putAll(buffer);
	}
}
