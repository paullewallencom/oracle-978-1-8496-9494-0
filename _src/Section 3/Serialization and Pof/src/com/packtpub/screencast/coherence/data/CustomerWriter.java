package com.packtpub.screencast.coherence.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.packtpub.screencast.coherence.customers.Customer;

public class CustomerWriter {

	public static void main(String[] args) throws IOException {
		CustomerGenerator cg = new CustomerGenerator();
		List<Customer> customers = cg.getCustomers(500);
		File file = new File(
				"D:\\Packtpub\\Coherence\\Sections\\Files\\customers.txt");
		FileWriter fw;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file.getAbsoluteFile());

			bw = new BufferedWriter(fw);
			String seperator = ";";
			String line;
			for (int i = 0; i < customers.size(); i++) {
				Customer customer = customers.get(i);
				line = customer.getId() + seperator + customer.getFirstName()
						+ seperator + customer.getLastName() + seperator
						+ customer.getCountry() + seperator
						+ customer.getZipCode() + seperator
						+ customer.getCitizenNo() + seperator
						+ customer.getAge();
				bw.write(line + "\n");
			}
		} catch (Exception e) {
			System.err.println("Error!!!");
		} finally {
			bw.close();
		}

	}
}
