package com.packtpub.screencast.coherence.cache;

import java.io.IOException;
import java.io.Serializable;

import com.packtpub.screencast.coherence.customers.Customer;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;
import com.tangosol.util.InvocableMap.Entry;
import com.tangosol.util.processor.AbstractProcessor;

public class CacheCustomerProcessor extends AbstractProcessor implements PortableObject, Serializable {

	
	public CacheCustomerProcessor()
	{
		
	}
	
	@Override
	public Object process(Entry entry) {
		// TODO Auto-generated method stub
		Customer customer = (Customer)entry.getValue();
		System.out.println("Incoming customer age:"+customer.getAge());
		customer.incrementAge();
		entry.setValue(customer);
		System.out.println("Customer new age:"+customer.getAge());
		return null;
	}

	@Override
	public void readExternal(PofReader reader) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeExternal(PofWriter writer) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
