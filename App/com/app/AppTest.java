package com.app;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppTest {
	@Test
	public void test() {
		ApplicationContext conte = new ClassPathXmlApplicationContext(
				"beans.xml");
		  DataSource dataSource=(DataSource)conte.getBean("dataSource");
		  try {
			Connection conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println( conte.getBean("dataSource"));
	}

	//@Test
	public void test1() {
		int q = 3;
		PropertyConfigurator.configure("classpath:Properties.properties");
		Logger log = Logger.getLogger(AppTest.class);
		log.debug("aaaa");
	}

	//@Test
	public void Test2() {
int a=100;
Integer b=100;
Integer c=new Integer(100);
System.out.println(a==b);
System.out.println(b==c);
System.out.println(a==c);
	}
}