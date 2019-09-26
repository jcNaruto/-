package com.jc.laboratory.util;

import org.junit.Test;

import com.jc.laboratory.BaseTest;

public class MD5UtilTest extends BaseTest {

	/**
	 * MD5 hash of an email address 
	 */
	@Test
	public void getEmailHash() {
		String lowerCase = "1932746543@qq.com".toLowerCase();
		String md5Hex = MD5Util.md5Hex(lowerCase);
		System.out.println(md5Hex);
	}
	
}
