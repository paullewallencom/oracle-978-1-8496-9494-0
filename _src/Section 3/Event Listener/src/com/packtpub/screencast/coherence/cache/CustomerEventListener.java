package com.packtpub.screencast.coherence.cache;

import com.packtpub.screencast.coherence.customers.Customer;
import com.tangosol.util.MapEvent;
import com.tangosol.util.MapListener;

public class CustomerEventListener implements MapListener {

	@Override
	public void entryDeleted(MapEvent me) {
		// TODO Auto-generated method stub

		Customer customer = (Customer) me.getOldValue();
		System.out.println("Event deleted:" + customer.getId());

	}

	@Override
	public void entryUpdated(MapEvent me) {
		// TODO Auto-generated method stub
		System.out.println("Event Updated");

		Customer customerOld = (Customer) me.getOldValue();
		Customer customerNew = (Customer) me.getNewValue();

		System.out.println("Event updated:" + customerNew.getId()
				+ ", Old First Name:" + customerOld.getFirstName()
				+ "; New First Name:" + customerNew.getFirstName());

	}
	
	@Override
	public void entryInserted(MapEvent me) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) me.getNewValue();
		System.out.println("Event Inserted:" + customer.getId());
	}

}
