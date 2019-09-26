package com.jc.laboratory;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置spring和Junit整合，加载spring的配置文件
 * 测试类的基础类
 * @author jc
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext-dao.xml","classpath:spring/applicationContext-service.xml","classpath:spring/applicationContext-trans.xml","classpath:spring/applicationContext-mail.xml","classpath:spring/applicationContext-redis.xml"})
public class BaseTest {

}
