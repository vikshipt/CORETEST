package com.testapp.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AppTester {
	private int counter;
	private Logger logger = LoggerFactory.getLogger(AppTester.class);
	public static ClassPathXmlApplicationContext ctx;
	public static ComboPooledDataSource dataSource;
	// private EmployeeDAO employeeDAO;

	public static void main(String[] args) {
		ctx = new ClassPathXmlApplicationContext("spring.xml");
		dataSource = ctx.getBean("dataSource", ComboPooledDataSource.class);
		for (int i = 1; i <= 6; i++) {
			new Thread(new TestThread(i)).start();
			try {
				Thread.sleep(1 * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("<=== Finished ===> ");
	}
}
