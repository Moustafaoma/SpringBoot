package com.example.SimpleCrudOnEmployee.Repositories.impl;
import com.example.SimpleCrudOnEmployee.Repositories.IEmployeeRepos;
import com.example.SimpleCrudOnEmployee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;


@Repository
public class EmployeeJDBCRepo implements IEmployeeRepos {
    private final JdbcTemplate jdbcTemplate;

    public EmployeeJDBCRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";
        return jdbcTemplate.query(sql, employeeRowMapper());
    }

    @Override
    public Employee findById(Long id) {
       String sql="SELECT * FROM employees WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, employeeRowMapper(), id);
    }

    @Override
    public int save(Employee employee) {
        try {
            String sql = "INSERT INTO employees (id, name, email, department_id) VALUES (emp_seq.NEXTVAL, ?, ?, ?)";
            return jdbcTemplate.update(sql, employee.getName(), employee.getEmail(), employee.getDepartmentId());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save employee: " + e.getMessage());
        }
    }


    @Override
    public int update(Employee employee) {
        String sql = "UPDATE employees SET name = ?, email = ?, department_id = ? WHERE id = ?";
        return jdbcTemplate.update(sql, employee.getName(), employee.getEmail(), employee.getDepartmentId(), employee.getId());
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int Count() {
        String sql = "SELECT COUNT(*) FROM Employees";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return (count != null) ? count : 0;
    }

    private RowMapper<Employee> employeeRowMapper() {
        return (rs, rowNum) -> {
            Employee employee = new Employee();
            employee.setId(rs.getLong("id"));
            employee.setName(rs.getString("name"));
            employee.setEmail(rs.getString("email"));
            employee.setDepartmentId(rs.getLong("department_id"));
            return employee;
        };
    }
}
