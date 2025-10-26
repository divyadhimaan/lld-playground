import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
class Address {
    private String address;
    Address(String address){
        this.address = address;
    }
}

@Getter
public final class Employee {
    private final int salary;
    private final String name;
    private final Address address;

    Employee(int salary, String name, Address address){
        this.salary = salary;
        this.name = name;
        this.address = new Address(address.getAddress());
    }

    int getSalary(){
        return this.salary;
    }

}


class Simulation {
    public static void main(String[] args){
        Address address1 = new Address("Bangalore");
        Employee emp1 = new Employee(20, "Bob", address1);
        Employee emp2 = new Employee(30, "Alice", address1);
        Employee emp3 = new Employee(20, "Charlie", address1);
        address1.setAddress("HYD");
//        System.out.println(emp.getAddress().getAddress());

        List<Employee> empList= new ArrayList<>();
        empList.add(emp1);
        empList.add(emp2);
        empList.add(emp3);

        List<Employee> sortedList = empList.stream().sorted(Comparator.comparingInt(Employee::getSalary).thenComparing(Comparator.comparing(Employee::getName).reversed())).toList();


        sortedList.forEach(e -> System.out.println(e.getName() + " " + e.getSalary()));

    }
}
