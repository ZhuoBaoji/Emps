package com.app;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.service.impl.EmpServiceImpl;

public class AppTest {
	@Test
	public void test() {
		ApplicationContext conte = new ClassPathXmlApplicationContext(
				"beans.xml");
		EmpServiceImpl ds = (EmpServiceImpl) conte.getBean("empservice");
		System.out.println(ds);
	}

	@Test
	public void test1() {
		int q = 3;
		PropertyConfigurator.configure("classpath:Properties.properties");
		Logger log = Logger.getLogger(AppTest.class);
		log.debug("aaaa");
	}

	@Test
	public void Test2() {
		AppTest a = new AppTest();

	}
}