package com.packtpub.screencast.coherence.cache;

import java.io.IOException;

import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;
import com.tangosol.util.Filter;
import com.tangosol.util.MapEvent;

public class UpdateFilter implements Filter, PortableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean evaluate(Object obj) {
		// TODO Auto-generated method stub
		MapEvent evt = (MapEvent) obj;
		if (evt.getId() == MapEvent.ENTRY_UPDATED) {
			return true;
		}
		return false;
	}

	@Override
	public void readExternal(PofReader arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeExternal(PofWriter arg0) throws IOException {
		// TODO Auto-generated method stub

	}

}
