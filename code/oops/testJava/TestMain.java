package testJava;

import java.util.List;
import java.util.stream.Collectors;

public class TestMain{
    public static void main(String[] args) {
        String str = "Hello World";
        System.out.println("Original String: " + str);

//         Attempting to change the string
        str.concat("!!!");
        System.out.println("After concat attempt: " + str);

        // Reassigning the string
        str = str.concat("!!!");
        System.out.println("After reassignment: " + str);

        List<String> names = List.of("Divya", "Amit", "Raj", "Sneha");
        System.out.println(names.stream()
                .filter(n -> n.endsWith("a"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList()));


    }
}