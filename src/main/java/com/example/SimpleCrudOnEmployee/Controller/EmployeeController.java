package com.example.SimpleCrudOnEmployee.Controller;
import com.example.SimpleCrudOnEmployee.Repositories.impl.EmployeeJDBCRepo;
import com.example.SimpleCrudOnEmployee.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeJDBCRepo employeeRepository;

    public EmployeeController(EmployeeJDBCRepo employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    @PostMapping
    @Transactional

    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        employeeRepository.save(employee);
        return ResponseEntity.ok("Employee added successfully!");
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        employeeRepository.update(employee);
        return ResponseEntity.ok("Employee updated successfully!");
    }
    @DeleteMapping("/{id}")
    @Transactional

    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeRepository.delete(id);
        return ResponseEntity.ok("Employee deleted successfully!");

    }

    @GetMapping("/count")
    public  int getCount(){
       return employeeRepository.Count();
    }
}
