package com.packtpub.screencast.coherence.cache;

import java.util.Scanner;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class CacheRemove {

	public static void main(String[] args) {
		NamedCache cache = CacheFactory.getCache("customers");

		System.out.println("Cache size:" + cache.size());

		String dummy;
		Scanner user_input = new Scanner(System.in);

		while (true) {
			
			System.out.println("Enter the customer id to remove:");
			dummy = user_input.next();
			cache.remove(dummy);
			System.out.println("Current cache size:"+cache.size());

		}
	}
}
