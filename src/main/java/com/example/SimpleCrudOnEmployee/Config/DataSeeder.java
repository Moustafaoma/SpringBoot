package com.example.SimpleCrudOnEmployee.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final String CREATE_SEQUENCE_DEPT_SEQ = "CREATE SEQUENCE dept_seq START WITH 1 INCREMENT BY 1";
    private static final String CREATE_SEQUENCE_EMP_SEQ = "CREATE SEQUENCE emp_seq START WITH 1 INCREMENT BY 1";

    private static final String CREATE_TABLE_DEPARTMENTS = """
        CREATE TABLE Departments (
            id NUMBER PRIMARY KEY,
            name VARCHAR2(255) NOT NULL
        )
        """;

    private static final String CREATE_TABLE_EMPLOYEES = """
        CREATE TABLE Employees (
            id NUMBER PRIMARY KEY,
            name VARCHAR2(255) NOT NULL,
            email VARCHAR2(500) NOT NULL,
            department_id NUMBER,
            FOREIGN KEY (department_id) REFERENCES Departments(id)
        )
        """;

    private static final String INSERT_INTO_DEPARTMENTS = "INSERT INTO Departments (id, name) VALUES (dept_seq.NEXTVAL, ?)";
    private static final String INSERT_INTO_EMPLOYEES = "INSERT INTO Employees (id, name, email, department_id) VALUES (emp_seq.NEXTVAL, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // Create sequences if they don't exist
        createSequences();

        // Create tables if they don't exist
        createTablesIfNotExist();

        // Insert data into Departments if not already present
        insertDepartmentsIfNotExist();

        // Insert Employees if no records exist
        insertEmployeesIfNeeded();

        System.out.println("Data seeding completed successfully!");
    }

    private void createSequences() {
        // Check if sequences exist, if not, create them
        String checkSeqQuery = "SELECT COUNT(*) FROM all_sequences WHERE sequence_name IN ('DEPT_SEQ', 'EMP_SEQ')";
        int count = jdbcTemplate.queryForObject(checkSeqQuery, Integer.class);
        if (count == 0) {
            jdbcTemplate.execute(CREATE_SEQUENCE_DEPT_SEQ);
            jdbcTemplate.execute(CREATE_SEQUENCE_EMP_SEQ);
        }
    }

    private void createTablesIfNotExist() {
        // Check if tables exist, if not, create them
        String checkDeptTableQuery = "SELECT COUNT(*) FROM user_tables WHERE table_name = 'DEPARTMENTS'";
        String checkEmpTableQuery = "SELECT COUNT(*) FROM user_tables WHERE table_name = 'EMPLOYEES'";
        int deptCount = jdbcTemplate.queryForObject(checkDeptTableQuery, Integer.class);
        int empCount = jdbcTemplate.queryForObject(checkEmpTableQuery, Integer.class);

        if (deptCount == 0) {
            jdbcTemplate.execute(CREATE_TABLE_DEPARTMENTS);
        }
        if (empCount == 0) {
            jdbcTemplate.execute(CREATE_TABLE_EMPLOYEES);
        }
    }

    private void insertDepartmentsIfNotExist() {
        // Check if departments are already inserted
        String checkDeptQuery = "SELECT COUNT(*) FROM Departments";
        int count = jdbcTemplate.queryForObject(checkDeptQuery, Integer.class);
        if (count == 0) {
            String[] departments = {"Finance", "HR", "IT", "Marketing", "Sales", "Operations"};
            for (String department : departments) {
                jdbcTemplate.update(INSERT_INTO_DEPARTMENTS, department);
            }
        }
    }

    private void insertEmployeesIfNeeded() {
        // Check if employees already exist
        String countQuery = "SELECT COUNT(*) FROM Employees";
        int count = jdbcTemplate.queryForObject(countQuery, Integer.class);
        if (count == 0) {
            insertEmployees();
        }
    }

    private void insertEmployees() {
        // Insert initial employee data
        jdbcTemplate.update(INSERT_INTO_EMPLOYEES, "John Doe", "john@example.com", 1);
        jdbcTemplate.update(INSERT_INTO_EMPLOYEES, "Jane Smith", "jane@example.com", 2);
        jdbcTemplate.update(INSERT_INTO_EMPLOYEES, "Alice Johnson", "alice@example.com", 3);
        jdbcTemplate.update(INSERT_INTO_EMPLOYEES, "Bob Brown", "bob@example.com", 4);
        jdbcTemplate.update(INSERT_INTO_EMPLOYEES, "Charlie Davis", "charlie@example.com", 5);
        jdbcTemplate.update(INSERT_INTO_EMPLOYEES, "Diana White", "diana@example.com", 1);
        jdbcTemplate.update(INSERT_INTO_EMPLOYEES, "Eve Black", "eve@example.com", 2);
        jdbcTemplate.update(INSERT_INTO_EMPLOYEES, "Frank Green", "frank@example.com", 3);
        jdbcTemplate.update(INSERT_INTO_EMPLOYEES, "Grace Miller", "grace@example.com", 4);
        jdbcTemplate.update(INSERT_INTO_EMPLOYEES, "Hank Wilson", "hank@example.com", 5);
    }
}
