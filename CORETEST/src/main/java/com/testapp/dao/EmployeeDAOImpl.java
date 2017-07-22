package com.testapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.testapp.model.Employee;

public class EmployeeDAOImpl implements EmployeeDAO {
	private DataSource dataSource;
	private Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);
	JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(Employee employee) {
		logger.debug("Request to add Emp: " + employee.getName());
		String query = "insert into Employee (name, role) values (?,?)";
		Object[] args = new Object[] { employee.getName(), employee.getRole() };
		int out = jdbcTemplate.update(query, args);
		if (out != 0) {
			logger.info("Employee saved with name=" + employee.getName());
		} else
			logger.info("Employee save failed with name=" + employee.getName());
		logger.error("testing error log");
	}

	@Override
	public Employee getById(int id) {
		String query = "select id, name, role from Employee where id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// using RowMapper anonymous class, we can create a separate RowMapper for reuse
		Employee emp = jdbcTemplate.queryForObject(query, new Object[] { id }, new RowMapper<Employee>() {
			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setRole(rs.getString("role"));
				return emp;
			}
		});
		return emp;
	}

	@Override
	public void update(Employee employee) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
