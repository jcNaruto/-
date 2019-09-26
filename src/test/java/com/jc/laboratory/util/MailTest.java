package com.jc.laboratory.util;

import javax.mail.MessagingException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;

import com.jc.laboratory.BaseTest;

/**
 * mail function test
 * @author jc
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MailTest extends BaseTest{
	
	@Autowired
	private MailUtil mailUtil;
	
	@Test
	public void sendAMail() {
		
			//mailUtil.sendMailByAsynchronousMode("jiachzhao@163,com", "test", "test....");
			
	}
	
	@Test
	public void sendBMail() throws Exception {
			//mailUtil.sendMailByAsynchronousMode("jiachzhao@163,com", "test", "test....");
			mailUtil.sendMail("1932746543@qq.com", "123", "123");
		
	}
	

}
