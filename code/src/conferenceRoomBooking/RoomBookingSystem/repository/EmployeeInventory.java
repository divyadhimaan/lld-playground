package RoomBookingSystem.repository;

import RoomBookingSystem.model.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EmployeeInventory {
    Map<UUID, Employee> employeeList;

    public EmployeeInventory() {
        this.employeeList = new HashMap<>();
    }

    public void addEmployee(Employee employee) {
        employeeList.put(employee.getEmployeeId(), employee);
    }

    public Employee getEmployeeByName(String name) {
        for (Employee e : employeeList.values()) {
            if (e.getEmployeeName().equals(name)) { //shouldn't ignore case here.
                return e;
            }
        }
        return null;
    }

    public Map<UUID, Employee> getAllEmployees() {
        return employeeList;
    }

    public boolean checkEmployeeExists(String name) {
        return getEmployeeByName(name) != null;
    }
}
