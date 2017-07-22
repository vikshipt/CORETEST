package com.testapp.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.testapp.dao.EmployeeDAO;
import com.testapp.model.Employee;

public class TestThread implements Runnable {
	private Logger logger = LoggerFactory.getLogger(TestThread.class);
	private EmployeeDAO employeeDAO;
	private int counter;
	private int thread_counter;
	JdbcTemplate jdbcTemplate;

	public TestThread(int thread_counter) {
		employeeDAO = AppTester.ctx.getBean("employeeDAO", EmployeeDAO.class);
		this.thread_counter = thread_counter;
		this.jdbcTemplate = new JdbcTemplate(AppTester.dataSource);
	}

	@Override
	public void run() {
		while (counter < 50) {
			logger.info("App is Running {}", counter);
			counter++;
			testingLog();
			logger.info(thread_counter + " End testing counter =========================> {}", counter);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.error("< testing error log >");
		}
	}

	private void testingLog() {
		String sql = "insert into employee(name,role) values(?,?)";
		Object[] args = new Object[] { "testing" + counter, "doctor" + counter };
		// jdbcTemplate = new JdbcTemplate(AppTester.dataSource);
		int out = jdbcTemplate.update(sql, args);
		if (out != 0) {
			logger.info("Employee saved with name= " + "testing" + counter);
		} else
			logger.info("Employee save failed with name=" + "testing" + counter);
	}

	private void testingLog1() {
		logger.info("Start testing counter {}", thread_counter); // Run some tests for JDBC CRUD operations
		Employee emp = new Employee();
		emp.setName("Amit" + counter);
		emp.setRole("core Developer" + counter); // Create
		employeeDAO.save(emp);
		logger.info("End testing counter =========================> {}", thread_counter);
	}
}
