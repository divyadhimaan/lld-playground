# Annotations

Spring Boot provides a variety of annotations to simplify development. 

These annotations help with 
- dependency injection, 
- configuration, 
- REST API development,
- transaction management.

## 1. Core Annotations

| Annotation                 | Description                                                                                                                  |
|----------------------------|------------------------------------------------------------------------------------------------------------------------------|
| `@SpringBootApplication`   | Marks the main class of a Spring Boot application (combines `@Configuration`, `@EnableAutoConfiguration`, `@ComponentScan`). |
| `@ComponentScan`           | Automatically scans and registers beans from the specified package.                                                          |
| `@Configuration`           | Marks a class as a source of bean definitions.    |
| `@Bean`                    | Defines a bean that Spring Boot should manage. |      

```java
//Sample
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}

```
	
## 2. Dependency Injection Annotations

| Annotation    | Description                                                                            |
|---------------|----------------------------------------------------------------------------------------|
| `@Component`	   | Generic annotation for creating a Spring-managed bean.                                 |
| `@Service`	     | Specialized `@Component` for service-layer logic.                                        |
| `@Repository`	  | Specialized `@Component` for DAO (Data Access Object) layer, adds exception translation. |
| `@Autowired`	   | Injects a dependency automatically.                                                    |      
| `@Qualifier("beanName")` | 	Specifies which bean to inject when multiple beans of the same type exist. | 
| `@Primary`	| Gives priority to a specific bean when multiple beans of the same type exist.|

```java
//Sample 
@Component
public class Engine {
    public void run() {
        System.out.println("Engine started...");
    }
}

@Service
public class Car {
    private final Engine engine;

    @Autowired
    public Car(Engine engine) {
        this.engine = engine;
    }

    public void start() {
        engine.run();
    }
}

```

## 3. REST API & Controller Annotations

| Annotation                                                     | Description                                                                            |
|----------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|
| `@Controller`	                                                 |Marks a class as a Spring MVC controller (for rendering views). |
| `@RestController`                                              | 	Combination of @Controller + @ResponseBody, used for REST APIs.
| `@RequestMapping("/path")`                                     | 	Maps HTTP requests to controller methods (supports GET, POST, etc.).                                  |  
| `@GetMapping`, `@PostMapping`,` @PutMapping`,` @DeleteMapping` | 	Shortcut for @RequestMapping(method = RequestMethod.GET/POST/PUT/DELETE). |
| `@PathVariable`                                                 | Extracts values from URL paths.                                                                                    |
| `@RequestParam`	                                                 | Extracts query parameters.                                                                                         |
| `@RequestBody`	                                                  | Maps request body to an object.                                                                                     |



```java
//This controller manages user-related operations.
//Requirements:
// 1. Get all users
// 2. Get a specific user by ID
// 3. Add a new user
// 4. Update an existing user
// 5. Delete a user


package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController  // Marks this class as a REST API controller
@RequestMapping("/api/users") // Base URL for this controller
public class UserController {

    private List<User> users = new ArrayList<>();

    // Constructor - Pre-populated users
    public UserController() {
        users.add(new User(1L, "John Doe", "john@example.com"));
        users.add(new User(2L, "Jane Doe", "jane@example.com"));
    }

    // 1. Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

    // 2. Get a specific user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // 3. Add a new user
    @PostMapping
    public String addUser(@RequestBody User user) {
        users.add(user);
        return "User added successfully!";
    }

    // 4. Update an existing user
    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                return "User updated successfully!";
            }
        }
        return "User not found!";
    }

    // 5. Delete a user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        users.removeIf(user -> user.getId().equals(id));
        return "User deleted successfully!";
    }

    // 6. Get user by name using RequestParam
    @GetMapping("/search")
    public User getUserByName(@RequestParam String name) {
        return users.stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}


```

## 4. Database & JPA Annotations
| Annotation                                          | Description                                                                            |
|-----------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|
| @Entity	                                            | Marks a class as a JPA entity (table in DB).
| @Table(name = "table_name")                         | 	Specifies a custom table name.
| @Id	                                                | Marks a field as the primary key.
| @GeneratedValue(strategy = GenerationType.IDENTITY) | 	Auto-generates primary key values.
| @Column(name = "column_name")                       | 	Maps a field to a specific database column.
| @OneToMany, @ManyToOne, @OneToOne, @ManyToMany	     | Defines relationships between entities.

```java
//Sample
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Getters and Setters
}

```


## 5. Transaction Management

| Annotation       | Description                                                                            |
|------------------|---------------------------------------------------------------------------------------------|
| @Transactional	  | Ensures a method runs within a transaction. |

```java
//Sample
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }
}

```

## 6. 