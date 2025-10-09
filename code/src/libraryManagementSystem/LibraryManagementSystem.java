import lombok.Getter;
import lombok.Setter;

import java.util.*;

class LibraryManagementInterface {

    private final LibraryManagementSystem libraryManagementSystem;

    public LibraryManagementInterface(String libraryId, Integer totalRacks) {
        this.libraryManagementSystem = new LibraryManagementSystem(libraryId, totalRacks);
        System.out.println("[INFO]    | Created library with " + totalRacks + " racks");
    }

    public void addBook(String bookId, String title, String authors, String publishingCompanies, String bookCopyIds){
        List<String> authorList = Arrays.asList(authors.split(","));
        List<String> publishingCompanyList = Arrays.asList(publishingCompanies.split(","));
        List<String> bookCopyIdList = Arrays.asList(bookCopyIds.split(","));

        libraryManagementSystem.addBook(bookId, title, authorList, publishingCompanyList, bookCopyIdList);
    }

    public void getBorrowLimitPerUser(){
        System.out.println("[INFO]    | Fetching borrow limit per user...");
        System.out.println("[DETAILS] | Borrow Limit Per User: " + libraryManagementSystem.getBorrowLimitPerUser());
    }

    public void displayRacks() {
        libraryManagementSystem.displayRacks();
    }

    public void removeBookCopy(String copyId) {
        libraryManagementSystem.removeBookCopy(copyId);
    }

    public void addUser(String userId, String name) {
        libraryManagementSystem.addUser(userId,name);
    }

    public void borrowBook(String bookId, String userId, Date borrowDate) {
        libraryManagementSystem.borrowBook(bookId, userId, borrowDate);
    }

    public void borrowBookCopy(String bookCopyId, String userId, Date borrowDate) {
        libraryManagementSystem.borrowBookCopy(bookCopyId, userId, borrowDate);
    }

    public void returnBookCopy(String bookCopyId){
        libraryManagementSystem.returnBookCopy(bookCopyId);
    }

    public void printBorrowedBooks(String userId){
        libraryManagementSystem.printBorrowedBooks(userId);
    }


}

@Getter
class LibraryManagementSystem {
    private final String libraryId;
    private final Integer borrowLimitPerUser = 5;
    private final Integer totalRacks;
    private final Map<Integer, Rack> racks; //rackNumber -> Rack
    private final Map<String, BookCopy> copyIdToBookCopy; //copyId -> BookCopy
    private final Map<String, LibraryUser> userIdToLibraryUser; //userId -> LibraryUser
    private final Map<String, Book> bookIdToBook; //bookId -> Book
    private final List<LibraryBorrowedLog> borrowedLogs;


    public LibraryManagementSystem(String libraryId, Integer totalRacks) {
        this.libraryId = libraryId;
        this.totalRacks = totalRacks;
        this.racks = new HashMap<>();
        for(int i=1; i<=totalRacks; i++){
            racks.put(i, new Rack(i)); //initially all racks are empty
        }
        this.copyIdToBookCopy = new HashMap<>();
        this.userIdToLibraryUser = new HashMap<>();
        this.bookIdToBook = new HashMap<>();
        this.borrowedLogs = new ArrayList<>();
    }

    public void addBook(String bookId, String title, List<String> authorList, List<String> publishingCompanyList, List<String> bookCopyIdList)
    {

        Book book = new Book(bookId, title, authorList, publishingCompanyList, bookCopyIdList);
        int availableRackCount = 0;

        // Step 1: Count racks available for this book (each rack can hold 1 copy)
        for (Rack rack : racks.values()) {
            if (rack.checkAvailabilityForBook(new BookCopy("temp", book, rack.getRackNumber()))) {
                availableRackCount++;
            }
        }

        // Step 2: Check if enough racks exist
        if (availableRackCount < bookCopyIdList.size()) {
            System.out.println("[ERROR]   | Rack not available for book ID: " + bookId);
            return ; // Reject this add request entirely
        }

        List<Integer> rackNumbers = new ArrayList<>();

        for (String copyId : bookCopyIdList) {
            for (int i = 1; i <= totalRacks; i++) { // ascending order
                Rack rack = racks.get(i);
                BookCopy newBookCopy = new BookCopy(copyId, book, rack.getRackNumber());
                if (rack.checkAvailabilityForBook(newBookCopy)) {
                    rack.addBookCopy(newBookCopy);
                    copyIdToBookCopy.put(copyId, newBookCopy);
                    rackNumbers.add(rack.getRackNumber());
                    break;
                }
            }
        }
        bookIdToBook.put(bookId, book);
        System.out.println("[INFO]    | Added Book to racks: " + rackNumbers);
    }

    public void removeBookCopy(String copyId) {
        BookCopy bookCopy = copyIdToBookCopy.get(copyId);
        if(bookCopy == null){
            System.out.println("[ERROR]   | Invalid Book Copy ID");
            return;
        }
        Rack rack = racks.get(bookCopy.getRackNumber());
        if (rack != null) {
            rack.removeBookCopy(copyId);
            copyIdToBookCopy.remove(copyId);
            System.out.println("[INFO]    | Removed book copy: "+ copyId + " from rack: " + rack.getRackNumber());


            boolean anyCopyLeft = copyIdToBookCopy.values().stream()
                    .anyMatch(c -> c.getBookId().equals(bookCopy.getBookId()));
            if (!anyCopyLeft) {
                bookIdToBook.remove(bookCopy.getBookId());
                System.out.println("[INFO]    | No more copies left for Book ID: " + bookCopy.getBookId() +
                        ". Book removed from library.");
            }
        }


    }

    public void addUser(String userId,String name) {
        LibraryUser user = new LibraryUser(userId, name, borrowLimitPerUser);
        userIdToLibraryUser.put(user.getUserId(), user);
        System.out.println("[INFO]    | Added user: " + name + " with User ID: " + user.getUserId());
    }

    public void borrowBook(String bookId, String userId, Date borrowDate) {
        LibraryUser user = userIdToLibraryUser.get(userId);
        if(!verifyUser(user, true))
            return;

        if(!bookIdToBook.containsKey(bookId)){
            System.out.println("[ERROR]   | Invalid Book ID");
            return;
        }

        // Find the first available book copy based on rack number
        BookCopy availableCopy = null;
        for (int i = 1; i <= totalRacks; i++) { // ascending rack order
            Rack rack = racks.get(i);
            for (BookCopy copy : rack.getBookCopyList()) {
                if (copy.getBookId().equals(bookId) && copy.isAvailable()) {
                    availableCopy = copy;
                    break;
                }
            }
            if (availableCopy != null) break;
        }

        if (availableCopy == null) {
            System.out.println("[ERROR]   | No available copies for Book ID: " + bookId);
            return;
        }


        Date dueDate = new Date(borrowDate.getTime() + (14L * 24 * 60 * 60 * 1000)); // 2 weeks from borrow date
        LibraryBorrowedLog log = new LibraryBorrowedLog(UUID.randomUUID().toString(), availableCopy, user, borrowDate, dueDate);
        borrowedLogs.add(log);

        // Mark the book copy as borrowed
        availableCopy.setBorrowedDetails(user, dueDate);
        user.addBorrowedBook(availableCopy);

        System.out.println("[INFO]    | User " + user.getName() + " borrowed book copy ID: " + availableCopy.getCopyId() + ", Due Date: " + dueDate + " from Rack: " + availableCopy.getRackNumber());
    }

    public void borrowBookCopy(String bookCopyId, String userId, Date borrowDate) {
        LibraryUser user = userIdToLibraryUser.get(userId);
        if(!verifyUser(user, true))
            return;

        BookCopy bookCopy = copyIdToBookCopy.get(bookCopyId);
        if(bookCopy == null){
            System.out.println("[ERROR]   | Invalid Book Copy ID");
            return;
        }
        if(!bookCopy.isAvailable()){
            System.out.println("[ERROR]   | Book Copy ID: " + bookCopyId + " is currently not available");
            return;
        }

        Date dueDate = new Date(borrowDate.getTime() + (14L * 24 * 60 * 60 * 1000)); // 2 weeks from borrow date
        LibraryBorrowedLog log = new LibraryBorrowedLog(UUID.randomUUID().toString(), bookCopy, user, borrowDate, dueDate);
        borrowedLogs.add(log);

        // Mark the book copy as borrowed
        bookCopy.setBorrowedDetails(user, dueDate);
        user.addBorrowedBook(bookCopy);

        System.out.println("[INFO]    | User " + user.getName() + " borrowed book copy ID: " + bookCopy.getCopyId() + ", Due Date: " + dueDate + " from Rack: " + bookCopy.getRackNumber());
    }

    private void returnBookCopyToRack(BookCopy bookCopy) {
        for (int i = 1; i <= totalRacks; i++) {
            Rack rack = racks.get(i);
            if (rack.checkAvailabilityForBook(bookCopy)) {
                rack.addBookCopy(bookCopy);
                bookCopy.setRackNumber(i); // if rackNumber is non-final
                return;
            }
        }
        System.out.println("[WARN] | No racks available to place returned book copy: " + bookCopy.getCopyId());
    }

    public void returnBookCopy(String bookCopyId){
        BookCopy bookCopy = copyIdToBookCopy.get(bookCopyId);
        if(bookCopy == null){
            System.out.println("[ERROR]   | Invalid Book Copy ID");
            return;
        }

        // Find the corresponding log
        for (LibraryBorrowedLog log : borrowedLogs) {
            if (log.getBookCopy().getCopyId().equals(bookCopyId) && log.getReturnDate() == null) {
                log.markAsReturned(new Date());
                break;
            }
        }

        // Mark the book copy as returned
        LibraryUser user = bookCopy.getBorrowedBy();
        bookCopy.setBorrowedDetails(null, null);
        if(user != null) {
            user.getBorrowedBooks().remove(bookCopy);
        }

        returnBookCopyToRack(bookCopy);

        System.out.println("[INFO]    | Book copy ID: " + bookCopyId + " has been returned and is now available in Rack: " + bookCopy.getRackNumber());
    }

    public void printBorrowedBooks(String userId){
        if(!verifyUser(userIdToLibraryUser.get(userId))){
            return;
        }

        LibraryUser user = userIdToLibraryUser.get(userId);
        List<BookCopy> borrowedBooks = user.getBorrowedBooks();
        if(borrowedBooks.isEmpty()) {
            System.out.println("[INFO]    | User " + user.getName() + " has no borrowed books.");
            return;
        }
        System.out.println("[INFO]    | User " + user.getName() + " has borrowed the following books:");

        for(BookCopy bookCopy: borrowedBooks){
            System.out.println("[DETAILS] | Book Copy: "+ bookCopy.getCopyId()+ " "+ " Title: "+bookCopy.getTitle()+ " Due Date: "+ bookCopy.getDueDate());
        }


    }

    private boolean verifyUser(LibraryUser user) {
        return verifyUser(user, false);
    }

    private boolean verifyUser(LibraryUser user, Boolean checkLimit) {
        if(user == null) {
            System.out.println("[ERROR]   | Invalid User ID");
            return false;
        }
        if(checkLimit && user.getBorrowedBooks() != null && user.getBorrowedBooks().size() >= borrowLimitPerUser) {
            System.out.println("[ERROR]   | Overlimit: User has reached the borrow limit");
            return false;
        }

        return true;
    }

    public void displayRacks() {
        System.out.println("Current state of racks:");
        for (Rack rack : racks.values()) {
            rack.displayBookCopies();
        }
    }
}

@Getter
class LibraryBorrowedLog {
    private final String logId;
    private final BookCopy bookCopy;
    private final LibraryUser user;
    private final Date borrowDate;
    private final Date dueDate;
    private Date returnDate;

    public LibraryBorrowedLog(String logId, BookCopy bookCopy, LibraryUser user, Date borrowDate, Date dueDate) {
        this.logId = logId;
        this.bookCopy = bookCopy;
        this.user = user;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
    }

    public void markAsReturned(Date returnDate) {
        this.returnDate = returnDate;
    }
}


@Getter
class Book {
    private final String bookId;
    private final String title;
    private final List<String> authors;
    private final List<String> publishingCompany;
    private final List<String> bookCopyIds;

    public Book(String bookId, String title, List<String> authors, List<String> publishingCompany, List<String> bookCopyIds) {
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.publishingCompany = publishingCompany;
        this.bookCopyIds = bookCopyIds;
    }
}
@Getter
class BookCopy extends Book {
    private final String copyId;
    private final Book book;
    @Setter
    private Integer rackNumber;
    private LibraryUser borrowedBy;
    private Date dueDate;

    public BookCopy(String copyId,Book book, Integer rackNumber) {
        super(book.getBookId(), book.getTitle(), book.getAuthors(), book.getPublishingCompany(), book.getBookCopyIds());
        this.copyId = copyId;
        this.book = book;
        this.rackNumber = rackNumber;
        this.borrowedBy = null;
        this.dueDate = null;
    }
    public Book getBookDetails() {
        return this.book;
    }

    public void setBorrowedDetails(LibraryUser user, Date dueDate) {
        this.borrowedBy = user;
        this.dueDate = dueDate;
    }

    public boolean isAvailable() {
        return this.borrowedBy == null;
    }

}

//@Getter
//class BookInventory {
//    private final Map<Book, List<BookCopy>> bookInventory;
//}

@Getter
class Rack {
    private final Integer rackNumber;
    private final List<BookCopy> bookCopyList; //each rack can hold at most one book copy
    private final Set<String> bookIdsInRack; //to ensure no duplicate books in the same rack

    public Rack(Integer rackNumber) {
        this.rackNumber = rackNumber;
        this.bookCopyList = new ArrayList<>();
        this.bookIdsInRack = new HashSet<>();
    }

    public void addBookCopy(BookCopy bookCopy) {
        if(checkAvailabilityForBook(bookCopy)) {
            this.bookCopyList.add(bookCopy);
            this.bookIdsInRack.add(bookCopy.getBookId());
        }else {
            System.out.println("[WARN]    | Skipping addition of duplicate book ID: " + bookCopy.getBookId());
        }
    }

    public void removeBookCopy(String copyId) {
        BookCopy toRemove = null;

        // Find the BookCopy by copyId
        for (BookCopy copy : bookCopyList) {
            if (copy.getCopyId().equals(copyId)) {
                toRemove = copy;
                break;
            }
        }

        // Remove if found
        if (toRemove != null) {
            bookCopyList.remove(toRemove);
            bookIdsInRack.remove(toRemove.getBookId()); // Update book tracking
        } else {
            System.out.println("[WARN]    | Book copy ID " + copyId + " not found in rack " + rackNumber);
        }
    }


    public boolean checkAvailabilityForBook(BookCopy bookCopy) {
        return !bookIdsInRack.contains(bookCopy.getBookId());
    }

    public void displayBookCopies() {
        System.out.println("Rack Number: " + rackNumber);
        if(bookCopyList.isEmpty()) {
            System.out.println(" - No book copies in this rack.");
            return;
        }
        for (BookCopy copy : bookCopyList) {
            System.out.println(" - Copy ID: " + copy.getCopyId() + ", Title: " + copy.getTitle());
        }

    }
}

@Getter
class LibraryUser {
    private final String userId;
    private final String name;
    private final Integer borrowLimit;
    private final List<BookCopy> borrowedBooks;

    public LibraryUser(String userId, String name, Integer borrowLimit){
        this.userId = userId;
        this.name = name;
        this.borrowLimit = borrowLimit;
        this.borrowedBooks = new ArrayList<>();
    }
    public void addBorrowedBook(BookCopy bookCopy) {
        if (borrowedBooks.size() < borrowLimit) {
            borrowedBooks.add(bookCopy);
        } else {
            System.out.println("[ERROR]   | User " + name + " has reached the borrow limit.");
        }
    }
}


