package com.kh.myapp.util;

import java.util.Base64;

public class Base {

	Base() {
	}
	
	// Generate Base64 String
	public static String get(byte[] byteArray) {
		String encoded = Base64.getEncoder().encodeToString(byteArray);
		
		return encoded;
	}
}
