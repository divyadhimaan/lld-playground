# What is Dependency Injection (DI)?

Dependency Injection is a design pattern where the Spring container injects dependencies (objects) into a class instead of the class creating them itself. This improves flexibility, testability, and maintainability.

```java
//Without DI
public class Car {
    private Engine engine = new Engine(); // Hardcoded dependency

    public void start() {
        engine.run();
    }
}
```
> Problem: `Car` is tightly coupled to `Engine`, making it difficult to replace `Engine` with another implementation.


## Using DI
Spring Boot uses IoC containers (like ApplicationContext) to manage dependencies. It automatically injects objects where needed, removing the need to manually instantiate them.

1. **Field Injection**:
   
   Using `@Component` and `@Autowired`
    ```java
    import org.springframework.stereotype.Component;
    
    @Component  // Marks this as a Spring-managed bean
    public class Engine {
        public void run() {
        System.out.println("Engine started...");
        }
    }
    ```
    
    ```java
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;
    
    @Component
    public class Car {
        @Autowired  // Injects Engine bean automatically
        private Engine engine;
    
        public void start() {
            engine.run();
        }
    }
    
    ```
   
2. **Constructor Injection**:
   
   Constructor-based DI is preferred because:

   - It ensures required dependencies are provided.
   - It improves testability.
   ```java
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Component;
   
   @Component
   public class Car {
       private final Engine engine;
   
       @Autowired
       public Car(Engine engine) { // Injects Engine via constructor
           this.engine = engine;
       }
   
       public void start() {
           engine.run();
       }
   }
   
   ```
   

3. **Setter Injections**:

   Setter injection is used when dependencies are optional.
   ```java
   import org.springframework.stereotype.Component;
   import org.springframework.beans.factory.annotation.Autowired;
   
   @Component
   public class Car {
      private Engine engine;
   
      @Autowired
      public void setEngine(Engine engine) {
         this.engine = engine;
      }
   
      public void start() {
         engine.run();
      }
   }
   
   ```
   

# What is Inversion of Control (IOC)?

IoC means the control of object creation is given to the Spring Container instead of manually creating objects.

Spring Boot automatically manages object lifecycles through IoC, making applications more modular and scalable.


```java
// Without IOC
public class App {
    public static void main(String[] args) {
        Engine engine = new Engine();
        Car car = new Car(engine);
        car.start();
    }
}

```

```java
// With IOC
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MySpringApp {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MySpringApp.class, args);
        Car car = context.getBean(Car.class); // IoC container manages dependencies
        car.start();
    }
}

```


## Types of Beans

Spring manages different types of beans:

- `@Component` → Generic bean
- `@Service` → Business logic layer
- `@Repository` → Data layer
- `@Controller`/`@RestController` → Web controllers