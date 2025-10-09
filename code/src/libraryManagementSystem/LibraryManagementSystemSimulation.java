import java.util.Date;

public class LibraryManagementSystemSimulation {
    public static void main(String[] args) {
        LibraryManagementInterface library = new LibraryManagementInterface("lib1", 10);

        // adding a book
        library.addBook(
                "B101",
                "Design Patterns",
                "Erich Gamma,Richard Helm,Ralph Johnson,John Vlissides",
                "Addison-Wesley",
                "C101,C102,C103,C104"
        );

        library.addBook(
                "B201",
                "Book-sample1",
                "Erich Gamma,Richard Helm,Ralph Johnson,John Vlissides",
                "Addison-Wesley",
                "C201,C202,C203,C204,C205,C206,C207,C208,C209,C210"
        );

        library.removeBookCopy("C102");
        library.removeBookCopy("C204");
        library.removeBookCopy("C304"); // removing non-existing copy

        library.addUser("user1", "Alice");
        library.addUser("user2","Bob");

        library.borrowBook("B101", "user1", new Date("2024/06/01"));
        library.borrowBook("B201", "user1", new Date("2024/06/01"));
        library.borrowBook("B101", "user2", new Date("2024/06/02"));

        library.borrowBookCopy("C205", "user2", new Date("2024/06/02"));
        library.borrowBookCopy("C206", "user1", new Date("2024/06/02"));
        library.borrowBookCopy("C207", "user1", new Date("2024/06/02"));
        library.borrowBookCopy("C207", "user2", new Date("2024/06/02")); //trying to borrow already borrowed copy
        library.borrowBookCopy("C208", "user1", new Date("2024/06/02"));
        library.borrowBookCopy("C209", "user1", new Date("2024/06/02")); //trying to borrow more than borrow limit

        library.returnBookCopy("C205");
        library.returnBookCopy("C208");

        library.printBorrowedBooks("user1");
        library.printBorrowedBooks("user2");
        library.printBorrowedBooks("user3"); // non-existing user


//        library.displayRacks();
    }
}
