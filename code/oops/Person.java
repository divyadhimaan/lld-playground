import java.util.Arrays;
import java.util.List;

// This file is used to depict classes and objects
public class Person{
    public final String firstName;
    public final String LastName;

    public Person(String fName, String lName){
        this.firstName = fName;
        this.LastName = lName;
    }

    public List<String> getName()
    {
        return Arrays.asList(this.firstName,this.LastName);
    }

    public static void main(String[] args)
    {
        Person p = new Person("Divya", "Dhiman");
        List<String> name= p.getName();
        System.out.println(name.get(0) + " " + name.get(1));
    }
}

