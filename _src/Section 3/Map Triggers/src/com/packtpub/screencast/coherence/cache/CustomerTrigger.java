package com.packtpub.screencast.coherence.cache;

import java.io.IOException;

import com.packtpub.screencast.coherence.customers.Customer;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;
import com.tangosol.util.MapTrigger;

public class CustomerTrigger implements MapTrigger, PortableObject {

	@Override
	public void readExternal(PofReader arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeExternal(PofWriter arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void process(Entry entry) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) entry.getValue();

		if (Integer.parseInt(customer.getId()) > 100) {
			throw new InvalidIdException(
					"Invalid age value. Age value must be lower than 150.");

		}
	}

}
