package com.kh.myapp.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	MD5() {}
	
	// Generate md5 hash of String
	public static String generate(String input) { 
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); 
            byte[] messageDigest = md.digest(input.getBytes()); 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            
            return hashtext; 
        } catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    }
	
}