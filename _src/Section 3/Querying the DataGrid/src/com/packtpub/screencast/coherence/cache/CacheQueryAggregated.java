package com.packtpub.screencast.coherence.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.packtpub.screencast.coherence.customers.Customer;
import com.packtpub.screencast.coherence.data.CustomerGenerator;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.Filter;
import com.tangosol.util.aggregator.DistinctValues;
import com.tangosol.util.aggregator.DoubleAverage;
import com.tangosol.util.aggregator.LongMax;
import com.tangosol.util.aggregator.LongMin;
import com.tangosol.util.extractor.ReflectionExtractor;
import com.tangosol.util.filter.AndFilter;
import com.tangosol.util.filter.GreaterFilter;
import com.tangosol.util.filter.InFilter;

public class CacheQueryAggregated {

	public static void main(String[] args) {
		// variable defining
		Set<String> countrySet = new HashSet<String>();
		NamedCache cache = CacheFactory.getCache("customers");

		// fills the cache
		System.out.println("Cache is being loaded...");
		fillTheCache(cache);

		System.out.println("Cache size:" + cache.size());

		// countrySet will be used for country filtering
		countrySet.add("Italy");
		countrySet.add("Japan");
		countrySet.add("England");

		// form the filter
		Filter customerFilter = new AndFilter(new GreaterFilter(
				new ReflectionExtractor("getId"), String.valueOf(1000)),
				new InFilter("getCountry", countrySet));

		Long maxAge = (Long) cache.aggregate(customerFilter, new LongMax(
				"getAge"));
		System.out
				.println("The maximum age of customers whose customer id is more than 1000 and country is any of the Italy,Japan,England:"
						+ maxAge);

		Long minAge = (Long) cache.aggregate(customerFilter, new LongMin(
				"getAge"));
		System.out
				.println("The minimum age of customers whose customer id is more than 1000 and country is any of the Italy,Japan,England:"
						+ minAge);

		Double averageAge = (Double) cache.aggregate(customerFilter,
				new DoubleAverage("getAge"));
		System.out
				.println("The average age of customers whose customer id is more than 1000 and country is any of the Italy,Japan,England:"
						+ averageAge);

		Set distinctCountries = (Set) cache.aggregate(customerFilter,
				new DistinctValues("getCountry"));

		Iterator<Map.Entry> countriesIterator = distinctCountries.iterator();
		while (countriesIterator.hasNext()) {

			System.out.println("Distinct Country:" + countriesIterator.next());
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
