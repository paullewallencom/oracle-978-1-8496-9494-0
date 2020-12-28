package com.packtpub.screencast.coherence.cache;

import java.util.Scanner;

import com.packtpub.screencast.coherence.customers.Customer;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class CacheGetOverflow {

	public static void main(String[] args) {
		NamedCache cache = CacheFactory.getCache("customers");
		System.out.println("Cache size:" + cache.size());
		Customer customer;

		String dummy;
		Scanner user_input = new Scanner(System.in);
		long t1, t2;

		final int frontCacheSize = 100000;
		final int backCacheSize = 100000;

		t1 = System.currentTimeMillis();
		for(int i=1; i<=frontCacheSize; i++)
		{
			customer = (Customer) cache.get(String.valueOf(i));
		}
		t2 = System.currentTimeMillis();
		System.out.println("Getting all items in the front cache:"+(t2-t1)+" ms.");
		
		t1 = System.currentTimeMillis();
		for(int i=frontCacheSize+1; i<=frontCacheSize+backCacheSize; i++)
		{
			customer = (Customer) cache.get(String.valueOf(i));
		}
		t2 = System.currentTimeMillis();
		System.out.println("Getting all items in the back cache:"+(t2-t1)+" ms.");

	}
}
