
## Bean
A bean is an object that is managed by the Spring Inversion of Control (IoC) container.

Beans are the backbone of a Spring application, as they define the core components that are instantiated, assembled, and managed by the Spring framework.

## Spring MVC (Model-View-Controller)

Spring MVC is a framework within the Spring ecosystem used for building web applications.

It follows the Model-View-Controller (MVC) design pattern, which helps in developing flexible, scalable, and maintainable web applications.

Key Components of Spring MVC
- DispatcherServlet:
    - The front controller that handles all incoming HTTP requests and routes them to appropriate handlers.
    - Defined in web.xml or configured in Java.
- Controller (@Controller / @RestController):
    - Processes user requests.
    - Uses @RequestMapping or @GetMapping to map URLs.
- Model:
    - Holds application data.
    - Passed to the view for rendering.
- View:
    - The SpotifyUi representation, usually JSP, Thymeleaf, or other template engines.
- ViewResolver:
    - Helps locate and render the correct view.

###  Spring MVC vs Spring Boot? 
- Spring MVC is part of the Spring Framework and requires more configuration.
- Spring Boot simplifies setup using auto-configuration and embedded servers.
