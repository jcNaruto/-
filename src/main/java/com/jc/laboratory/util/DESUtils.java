package com.jc.laboratory.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * DES encrypt 
 * @author jc
 *
 */
public class DESUtils {

	private static Key key;
	private static String KEY_STR = "myKey";
	private static String CHARSETNAME = "UTF-8";
	private static String ALGORITHM = "DES";

	static {
		try {
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(KEY_STR.getBytes());
			generator.init(secureRandom);
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getEncryptString(String str) {
		BASE64Encoder base64encoder = new BASE64Encoder();
		try {
			byte[] bytes = str.getBytes(CHARSETNAME);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] doFinal = cipher.doFinal(bytes);
			return base64encoder.encode(doFinal);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	public static String getDecryptString(String str) {
		BASE64Decoder base64decoder = new BASE64Decoder();
		try {
			byte[] bytes = base64decoder.decodeBuffer(str);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] doFinal = cipher.doFinal(bytes);
			return new String(doFinal, CHARSETNAME);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * WnplV/ietfQ=
		um461kxL7IU=
		zm//IxgTa4aVRD2zF8Bu1VZ5d1S1jFAj
		8SdaarMBgvVIuhUmKCjOjQ==
		Rrm2jKPjEfUfJAfVsP+M2w==
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getEncryptString("jiachzh@qq.com"));
		System.out.println(getEncryptString("mqfespccqfupiicj"));
	}

}