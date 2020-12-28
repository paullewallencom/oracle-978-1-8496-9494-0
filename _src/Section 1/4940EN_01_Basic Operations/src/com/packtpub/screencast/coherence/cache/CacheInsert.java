package com.packtpub.screencast.coherence.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import com.packtpub.screencast.coherence.customers.Customer;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class CacheInsert {

	public static void main(String[] args) throws IOException {
		NamedCache cache = CacheFactory.getCache("customers");

		String dummy = "";

		File file = new File("D:\\Packtpub\\Coherence\\Sections\\Files\\customers.txt");
		FileReader fr = null;
		BufferedReader br = null;

		String line;
		String fields[];
		String seperator = ";";
		try {

			fr = new FileReader(file);
			br = new BufferedReader(fr);

			while ((line = br.readLine()) != null) {
				fields = line.split(seperator);
				Customer customer = new Customer(fields[0], fields[1],
						fields[2], fields[3], Integer.parseInt(fields[4]),
						fields[5], Integer.parseInt(fields[6]));
				// it puts the object into the cache
				cache.put(customer.getId(), customer);
			}

		} catch (Exception ex) {
			System.out.println("Error:" + ex.getMessage());
		} finally {
			br.close();
			fr.close();
		}

		System.out.println("Cache size:" + cache.size());

		Scanner user_input = new Scanner(System.in);
		dummy = user_input.next();

	}

}
