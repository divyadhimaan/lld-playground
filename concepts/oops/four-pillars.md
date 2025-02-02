# Four Pillars of Object Oriented Programming



## A. Encapsulation

Encapsulation is the concept of hiding the implementation details of an object from the outside world and only exposing the necessary information through public methods.

>It is the mechanism that binds together the code and the data it manipulates. 


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
```

Here the encapsulation is done using private data members, restricting direct access.
You can only access them using getters and setters.


## B. Abstraction

> Abstraction is the concept of showing only the necessary information to the outside world while hiding unnecessary details.

Abstraction helps to simplify complex systems and focus on the essential features.

In Java, abstraction is achieved by ```interfaces``` and ```abstract classes```