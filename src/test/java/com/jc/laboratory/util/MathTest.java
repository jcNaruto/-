package com.jc.laboratory.util;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.jc.laboratory.BaseTest;

public class MathTest extends BaseTest{

	@Test
	public void randomTest() {
		System.out.println((int)((Math.random()*9+1)*10000));
	}
	
	
	@Test
	public void uuidTest() {
		String uuid = MathUtil.getUUID();
		System.out.println(uuid);
	}
	@Test
	public void imgTest() {
		ClassPathResource resource = new ClassPathResource("dlut.png");
		System.out.println(resource);
	}
}
