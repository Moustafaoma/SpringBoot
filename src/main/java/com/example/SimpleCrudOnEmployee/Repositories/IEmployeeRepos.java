package com.example.SimpleCrudOnEmployee.Repositories;

import com.example.SimpleCrudOnEmployee.model.Employee;

import java.util.List;

public interface IEmployeeRepos {
    public List<Employee> findAll();
    public Employee findById(Long id );
    public int save(Employee employee);
    public int update(Employee employee);
    public int delete(Long id);
    public  int Count();

}
