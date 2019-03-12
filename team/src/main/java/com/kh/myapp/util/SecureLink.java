package com.kh.myapp.util;

import java.util.Base64;
import java.util.Date;

public class SecureLink {
	
	public SecureLink() {
	}
	
	// Validate SecureLink String
	public static boolean isValidate(String secureLinkStr, String key, String plainKey, long timeout) {
		String instanceToken = get(key, plainKey, timeout);
		if (secureLinkStr.equals(instanceToken)) {
			return true;
		} else {
			return false;
		}
	}
	
	// Generate SecureLink String
	@SuppressWarnings("deprecation")
	public static String get(String key, String plainKey, long timeout) {
		Date dates = new Date();
		long time = dates.getTime() + timeout;
		Date timestamp = new Date(time * 1000);

		timestamp.setHours(0);
		timestamp.setSeconds(0);
        timestamp.setMinutes(0);
		long unixtime = timestamp.getTime() / 1000;
		
		String hash = key + "/" + "/" + plainKey + "/" + unixtime;
		String md5hash = MD5.generate(hash);
		byte[] byteArray = com.kh.myapp.util.byteArray.hexStringToByteArray(md5hash);

        String encoded = Base64.getEncoder().encodeToString(byteArray);
        encoded = encoded.replaceAll("-", "_");
        encoded = encoded.replaceAll("/", "-");
        encoded = encoded.replaceAll("([=])", "");
		return encoded;
	}
	
}