# Class and Objects


## Class and Objects

A ```class``` is a blueprint or template that defines the properties and behavior of an object.

In general, class declarations can include these components in order:

- **Modifiers**: A class can be public or have default access
- **Class name**: The class name should begin with the initial letter capitalized by convention.
- **Body**: The class body is surrounded by braces, { }.

An ```Object``` is an instances of a class, created using the class definition.


### Sample
```
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

    public static void src.main.java.CacheDemo(String[] args)
    {
        Person p = new Person("Divya", "Dhiman");
        List<String> name= p.getName();
        System.out.println(name.get(0) + " " + name.get(1));
    }
}