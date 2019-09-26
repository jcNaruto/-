package com.jc.laboratory.util;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.jc.laboratory.BaseTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisTest extends BaseTest{

	@Autowired
	private JedisClientPool jedis;
	
	@Test
	public void initialTest() {
		jedis.set("first", "100");
		String result = jedis.get("first");
		System.out.println(result);
	}
	
}
