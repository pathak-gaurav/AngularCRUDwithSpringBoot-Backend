package github.pathakgaurav.employeemanager.service;

import github.pathakgaurav.employeemanager.exception.UserNotfoundException;
import github.pathakgaurav.employeemanager.model.Employee;
import github.pathakgaurav.employeemanager.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Employee saveEmployee(Employee employee) {
        employee.setEmployeeCode(UUID.randomUUID().toString());
        return employeeRepository.save(employee);
    }

    public List<Employee> findAllEmployee() {
        if (employeeRepository.findAll().isEmpty()) {
            List<Employee> employeeList = Arrays.asList(
                    new Employee("Zack Anderson", "zack@anderson.com", "Software Engineer", "",
                                 UUID.randomUUID().toString()),
                    new Employee("Cody Anderson", "cody@anderson.com", "Sr Software Engineer", "",
                                 UUID.randomUUID().toString()));
            employeeRepository.saveAll(employeeList);

        }
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public List<Employee> deleteEmployeeById(Long id) {
        employeeRepository.deleteEmployeeById(id);
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepository.findEmployeeById(id)
                .orElseThrow(() -> new UserNotfoundException("User by id " + id + " was not found"));
    }

    public List<Employee> findByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email).orElseThrow(() -> new UserNotfoundException("User by id " + email + " was not found"));
    }
}
