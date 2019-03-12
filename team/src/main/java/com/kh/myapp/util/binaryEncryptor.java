package com.kh.myapp.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Base64;

import javax.xml.bind.DatatypeConverter;

public class binaryEncryptor {
	
	public static String toHex(String arg) {
	    return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
	}
	
	public static String binToHex(String s) {
		return new BigInteger(s, 2).toString(16);
	}
	
	public static String hexToBin(String s) {
		return new BigInteger(s, 16).toString(2);
	}

	public static String octToStr(String s) {
		return new BigInteger(s, 16).toString(10);
	}
	
	public static String strToOct(String s) {
		return new BigInteger(s, 10).toString(16);
	}
	
	public static String fromHex(String hex) throws UnsupportedEncodingException {
	    hex = hex.replaceAll("^(00)+", "");
	    byte[] bytes = DatatypeConverter.parseHexBinary(hex);
	    return new String(bytes, "UTF-8");
	}
	
	public static String Decrypt(String plainText) {
		plainText = plainText.replaceAll("_", "-");
		plainText = plainText.replaceAll("-", "/");
		byte[] plainByte = Base64.getDecoder().decode(plainText.getBytes());
		plainText = new String(plainByte);

		if (plainText.isEmpty()) return "";
		
		plainText = octToStr(plainText);
		
		String bytes = "";
		String queue = makePrefix(plainText, "Decrypt");
		queue = queue(queue, "Decrypt");
		String hexStr = binToHex(queue);

		try {
			bytes = fromHex(hexStr);
			return bytes;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bytes;
	}
	
	public static int binaryStringToDecimal(String biString){
	  int n = biString.length();      
	  int decimal = 0;
	  for (int d = 0; d < n; d++){
	    // append a bit=0 (i.e. shift left) 
	    decimal = decimal << 1;

	    // if biStr[d] is 1, flip last added bit=0 to 1 
	    if (biString.charAt(d) == '1'){
	      decimal = decimal | 1; // e.g. dec = 110 | (00)1 = 111
	    }
	  }
	  return decimal;
	}
	
	public static int getBitCount(int i) {
		i = i - ((i >> 1) & 0x55555555);
	    i = (i & 0x33333333) + ((i >> 2) & 0x33333333);
	    int xs = (((i + (i >> 4)) & 0x0F0F0F0F) * 0x01010101) >> 24;
		
	    return xs;
	}
	
	public static int getC64(int a, int b, int c, int d, int v, int r) {
		  // Do a normal parallel bit count for a 64-bit integer,                     
		  // but store all intermediate steps.                                        
		  // a = (v & 0x5555...) + ((v >> 1) & 0x5555...);
		  a =  v - ((v >> 1) & ~ 0xffffffff /3);
		  // b = (a & 0x3333...) + ((a >> 2) & 0x3333...);
		  b = (a & ~0xffffffff/5) + ((a >> 2) & ~0xffffffff/5);
		  // c = (b & 0x0f0f...) + ((b >> 4) & 0x0f0f...);
		  c = (b + (b >> 4)) & ~0xffffffff/0x11;
		  // d = (c & 0x00ff...) + ((c >> 8) & 0x00ff...);
		  d = (c + (c >> 8)) & ~0xffffffff/0x101;
		  int t = (d >> 32) + (d >> 48);
		  // Now do branchless select!                                                
		  int s  = 64;
		  if (r > t) {s -= 32; r -= t;}
		  s -= ((t - r) & 256) >> 3; r -= (t & ((t - r) >> 8));
		  t  = (d >> (s - 16)) & 0xff;
		  if (r > t) {s -= 16; r -= t;}
		  s -= ((t - r) & 256) >> 4; r -= (t & ((t - r) >> 8));
		  t  = (c >> (s - 8)) & 0xf;
		  if (r > t) {s -= 8; r -= t;}
		  s -= ((t - r) & 256) >> 5; r -= (t & ((t - r) >> 8));
		  t  = (b >> (s - 4)) & 0x7;
		  if (r > t) {s -= 4; r -= t;}
		  s -= ((t - r) & 256) >> 6; r -= (t & ((t - r) >> 8));
		  t  = (a >> (s - 2)) & 0x3;
		  if (r > t) {s -= 2; r -= t;}
		  s -= ((t - r) & 256) >> 7; r -= (t & ((t - r) >> 8));
		  t  = (v >> (s - 1)) & 0x1;
		  if (r > t) s--;
		  s -= ((t - r) & 256) >> 8;
		  s = 65 - s;
		  
		  return s;
	}
	
	public static String Encrypt(String plainText) {
		String hex = toHex(plainText);
		String bin = hexToBin(hex);
		
		int i = binaryStringToDecimal(bin);
		int isAccepted = ((i >> 4) % 6);
		System.out.println(isAccepted);
		if (isAccepted == 0 || isAccepted == 2) return "";
		if (isAccepted < 0) return "";
	
		String queue = queue(bin, "Encrypt");
		queue = makePrefix(queue, "Encrypt");

		queue = strToOct(queue);
		String encoded = Base64.getEncoder().encodeToString(queue.getBytes());
        encoded = encoded.replaceAll("-", "_");
        encoded = encoded.replaceAll("/", "-");
		return encoded;
	}

	public static String makePrefix(String plainBin, String Mode) {
		String Result = null;
		switch (Mode) {
			case "Encrypt":
				Result = makePrefixEncrypt(plainBin);
				break;
			case "Decrypt":
				Result = makePrefixDecrypt(plainBin);
				break;
			default:
				break;
		}
		
		return Result;
	}

	protected static String makePrefixDecrypt(String plainBin) {
		String carry;
		String result = "";
		int length;
		length = plainBin.length();
		
		if (plainBin.substring(0, 1) == "0") {
			carry = plainBin.substring(1,2);
			result = new StringBuilder(plainBin).replace(1, 2, carry).toString();
			result = new StringBuilder(result).replace(length - 1, length, carry).toString();
		}
		
		if (result.isEmpty()) {
			return plainBin;
		}
		
		return result;
	}
	
	protected static String makePrefixEncrypt(String plainBin) {
		String result = "";
		int length;
		length = plainBin.length();
		
		if (length > 1 && plainBin.substring(0, 1) == plainBin.substring(length -1,length)) {
			result = "0" + plainBin.substring(0 ,1) + plainBin.substring(1, length - 1);
		}
		
		if (result.isEmpty()) {
			return plainBin;
		}
		
		return result;
	}

	protected static String queueDecrypt(String plainBin) {
		int length;
		int intVal;
		String result = "";
		
		length = plainBin.length();
		for (int i=0;i < length;i++) {
			// Get bit count
			intVal = Integer.parseInt(plainBin.substring(i, i+1));
			// Count count to bit
			result += (intVal > 5) ? repeat("0", intVal - 5) : repeat("1", intVal);
		}
		
		return result;
	}
	
	public static String repeat(String str, int times) {
		 return new String(new char[times]).replace("\0", str);
	}
	
	/*
	 * Get binary bit count order by bit boolean (110001001 -> 28171)
	 * FALSE => (5 + bit count)
	 */
	protected static String queueEncrypt(String plainBin) {
		String sStr = "";
		int intVal = 0;
		int tmpVal = 0;
		int intBool;
		int length;
		
		intBool = -1;

		length = plainBin.length();
		
		// Loop in bit
		for(int i=0;i <= length; i++) {
			if (i == length) {
				// Append last bit count
				String Prefix = (intBool == 0) ? Integer.toString(5 + tmpVal) : Integer.toString(tmpVal);
				sStr += Prefix;
			}else {
				// Get a bit
				intVal = Integer.parseInt(plainBin.substring(i, i + 1));
			}
			
			if (intBool == -1) {
				// Set bit boolean type
				intBool = intVal;
			} else if ( (tmpVal > 3 && intVal == intBool) || (intVal != intBool) ) {
				/*
				 * Append bit count
				 * TRUE => (bit count)
				 * FALSE => (5 + bit count)
				 */
				String Prefix = (intBool == 0) ? Integer.toString(5 + tmpVal) : Integer.toString(tmpVal);
				sStr += Prefix;
				
				// Empty bit count
				tmpVal = 0;
				
				// Set bit boolean type
				if (intVal != intBool) {
					intBool = intVal;
				}
			}
			
			// Count bit count
			++tmpVal;
		}
		
		return sStr;
	}
	
	public static String intersect(String plainBin) {
		int carry;
		int length = plainBin.length();
		int halfLength = (plainBin.length() / 2);
		
		String tmpVal;
		String result = "";
		
		Float len = new Float(halfLength);
		for(int i=0;i<=len;i++) {
			carry = i % 2;
			if (carry == 1) {
				tmpVal = plainBin.substring(i, i+1);
				String replacementStr = plainBin.substring(length - 1, length);
				
				result = new StringBuilder(plainBin).replace(i, i + 1, replacementStr).toString();
				result = new StringBuilder(plainBin).replace(i, i + 1, tmpVal).toString();
			}
		}
		
		return result;
	}
	
	public static String queue(String plainBin, String Mode) {
		String Result = null;
		switch (Mode) {
			case "Encrypt":
				Result = queueEncrypt(plainBin);
				break;
			case "Decrypt":
				Result = queueDecrypt(plainBin);
				break;
			default:
				break;
		}
		
		return Result;
	}
	
	public static String ascii2hex(String ascii) {
	      char[] ch = ascii.toCharArray();

	      StringBuilder builder = new StringBuilder();

	      for (char c : ch) {
	    	  int i = (int) c;
	    	  builder.append(Integer.toHexString(i).toUpperCase());
	      }
	      
	      return builder.toString();
	}

}
