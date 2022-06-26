package compass.homework_2_8.service;
import compass.homework_2_8.exception.EmployeeAlreadyAddedException;
import compass.homework_2_8.exception.EmployeeNotFoundException;
import compass.homework_2_8.exception.EmployeeStorageIsFullException;
import compass.homework_2_8.model.Employee;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class EmployeeService {

    private static final int LIMIT = 10;

    private final Map<String, Employee> employees = new HashMap<>();

    private String getKey(String name, String surname){
        return name+ "|" + surname;
    }

    public Employee add(String name, String surname, int department, double salary) {
        Employee employee = new Employee(name, surname, department, salary);
        String key = getKey(name, surname);
        if (employees.containsKey(key)){
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size()<LIMIT){
            return employees.put(key, employee);
        }
        throw new EmployeeStorageIsFullException();
    }

    public Employee remove(String name, String surname) {
        String key = getKey(name, surname);
        if (!employees.containsKey(key)){
            throw new EmployeeNotFoundException();
        }
        return employees.remove(key);
    }

    public Employee find(String name, String surname) {
        String key = getKey(name, surname);
        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException();
        }
        return employees.get(key);
    }

    public List<Employee> getAll(){
        return new ArrayList<>(employees.values());
    }
}