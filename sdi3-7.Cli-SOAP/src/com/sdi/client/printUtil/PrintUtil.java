package com.sdi.client.printUtil;

import com.sdi.ws.AddressPoint;

public class PrintUtil {
	
	
	public static String formatAddress(AddressPoint adP){
		
		return adP.getCity() + " - " + adP.getState() + " - " + adP.getCountry() +
				" " + adP.getZipCode();

		
	}

}
