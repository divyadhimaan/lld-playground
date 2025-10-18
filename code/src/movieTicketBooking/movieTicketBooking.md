# Movie Ticket Booking System


## Class Structure

### 1. App Layer
- **`TicketBookingSimulation`**
    - Main entry point.
    - Handles CLI interactions for **Admin** and **Customer**.
    - Calls `TicketBookingController` for operations.
    - Menus: Add Theatre, Add Show, Update Seats, Book Show, etc.

### 2. Controller Layer (Facade)
- **`TicketBookingController`**
    - Exposes system functionality to the app.
    - Delegates operations to `TicketBookingService`.
    - Methods: registerUser, addTheatre, add/removeShow, updateSeating, displayShows, booking, getBookings.

- **`BookingManager`**
    - Handles end-to-end **booking workflow**.

- **`PaymentProcessor`**
    - Handles payment workflow via **strategy pattern** (UPI or Credit Card).

### 3. Service Layer
- **`TicketBookingService`** (Singleton)
    - Core business logic.
    - Coordinates UserFactory, TheatreFactory, UserInventory, TheatreInventory, BookingManager.
    - Handles: user registration, theatre registration, show management, booking management, seating updates.
- **`PaymentProcessor`**
- Handles payment workflow using **strategy pattern** (UPI or Credit Card).

### 4. Factory Layer
- **`UserFactory`**
    - Creates `User` objects (Admin/Customer).
    - Admin verification via code.
- **`TheatreFactory`**
    - Creates `Theatre` objects.
    - Verifies admin user.

### 5. Repository Layer (Singletons)
- **`UserInventory`**
    - Stores users (`Map<String, User>`).
    - Admin verification.
- **`TheatreInventory`**
    - Stores theatres (`Map<String, Theatre>`) and movies.
    - Methods to add/remove shows, update seating, get theatres/movies/shows.

### 6. Model Layer
- **`User`**
    - Attributes: userId, userName, userType, bookings.
    - Methods: addBooking, displayInfo.

- **`Theatre`**
    - Attributes: theatreId, theatreName, showsAvailable, registeredBy.
    - Methods: addShow, displayInfo, displayUpcomingShows.

- **`Movie`**
    - Attributes: movieId, movieName, theatres list.

- **`Show`**
    - Attributes: showId, date/time, seats, seat pricing.
    - Methods: calculatePrice, hold/bookSeats, displaySeats.

- **`Seat`**
    - Attributes: seatId, seatType, seatStatus.
    - Methods: hold(), book(), display().

- **`Booking`** (Builder pattern)
    - Attributes: bookingId, user, theatre, movie, show, seats, payment.
    - Method: displayBooking().

- **`Selection`**
    - Tracks current user selection (movie, theatre, show, seats).
    - Methods: chooseMovie, chooseTheatre, chooseShow, chooseSeats, displaySelection.

- **`Payment`**
    - Attributes: paymentId, strategy, amount, status.
    - Methods: processPayment(), displayPaymentInfo().

### 7. Strategy Layer (Payment)
- **`PaymentStrategy`** (interface)
- **`CreditCardPayment`** & **`UpiPayment`** (implement `PaymentStrategy`)

### 8. Enums
- `UserType`: ADMIN, CUSTOMER
- `SeatType`: REGULAR, PREMIUM
- `SeatStatus`: AVAILABLE, HELD, BOOKED
- `PaymentStatus`: PENDING, SUCCESS, FAILED


## Key Design Patterns Used
- **Singleton:** `TicketBookingService`, `UserInventory`, `TheatreInventory`.
- **Factory:** `UserFactory`, `TheatreFactory`.
- **Builder:** `Booking` object creation.
- **Strategy:** `PaymentStrategy` for payments.
- **Facade:** `TicketBookingController` exposing simplified interface.


```mermaid

classDiagram
    %% ===== Main App =====
    class TicketBookingSimulation {
        +main(args: String[])
        -handleAdmin(controller, scanner)
        -handleCustomer(controller, scanner)
        -addShow(controller, scanner, adminId)
        -updateSeats(controller, scanner, adminId)
        -removeShow(controller, scanner, adminId)
    }

    %% ===== Controller Layer =====
    class TicketBookingController {
        -service: TicketBookingService
        +registerUser(userType, userName, password): String
        +addTheatre(userId, theatreName): String
        +addShow(userId, theatreId, movieName, showDate, startTime, endTime)
        +displayUpcomingShowsForTheatre(theatreId)
        +displayAllUpcomingShows()
        +bookShow(userId)
        +getBookingsForUser(userId)
        +removeMovie(userId, theatreId, movieName)
        +removeShow(userId, theatreId, movieName, showId): Boolean
        +updateSeating(userId, theatreId, movieName, showId, newSeats)
    }

    class BookingManager {
        -theatreInventory: TheatreInventory
        +bookMovie(user)
    }

    class PaymentProcessor {
        +initiatePayment(scanner, amount): Payment
    }

    %% ===== Service Layer =====
    class TicketBookingService {
        -userFactory: UserFactory
        -userInventory: UserInventory
        -theatreFactory: TheatreFactory
        -theatreInventory: TheatreInventory
        -bookingManager: BookingManager
        +registerUser(userType, userName, password): String
        +registerTheatre(userId, theatreName): String
        +addShowToTheatre(userId, theatreId, movieName, showDate, startTime, endTime)
        +displayShowForTheatre(theatreId)
        +removeMovie(userId, theatreId, movieName)
        +removeShow(userId, theatreId, movieName, showId): Boolean
        +updateSeating(userId, theatreId, movieName, showId, newSeats)
        +displayAllUpcomingShows()
        +booking(userId)
        +getBookingsForUser(userId)
    }

    %% ===== Model Layer =====
    class User {
        -userId: String
        -userType: UserType
        -userName: String
        -password: String
        -bookings: List~Booking~
        +displayInfo()
        +addBooking(booking)
    }

    class Theatre {
        -theatreId: String
        -theatreName: String
        -numberOfScreens: int
        -showsAvailable: Map~Movie, List~Show~~
        -registeredBy: User
        +addShow(movie, show)
        +displayInfo()
        +displayUpcomingShows()
    }

    class Movie {
        -movieId: String
        -movieName: String
        -theatres: List~Theatre~
    }

    class Show {
        -showId: String
        -showDate: Date
        -startTime: LocalDateTime
        -endTime: LocalDateTime
        -seats: List~Seat~
        -seatPricing: Map~SeatType, Double~
        +changeStatus(selectedSeats, SeatStatus)
        +getSeatById(seatId): Seat
        +displaySeats()
        +calculateTotalPrice(selectedSeats): double
    }

    class Seat {
        -seatId: String
        -seatType: SeatType
        -seatStatus: SeatStatus
        +hold(): boolean
        +book(): boolean
        +isAvailable(): boolean
        +display()
    }

    class Booking {
        -bookingId: String
        -bookingBy: User
        -bookedTheatre: Theatre
        -bookedMovie: Movie
        -bookedShow: Show
        -bookedSeats: List~Seat~
        -payment: Payment
        +displayBooking()
    }

    class Payment {
        -paymentId: String
        -strategy: PaymentStrategy
        -amount: double
        -status: PaymentStatus
        +processPayment()
        +displayPaymentInfo()
    }

    class Selection {
        -selectedMovie: Movie
        -selectedTheatre: Theatre
        -selectedShow: Show
        -selectedSeats: List~Seat~
        -theatreShowsMap: Map~Theatre, List~Show~~
        +chooseMovie(scanner, inventory): boolean
        +chooseTheatre(scanner, inventory): boolean
        +chooseShow(scanner, inventory): boolean
        +chooseSeats(scanner): boolean
        +displaySelection()
    }

    %% ===== Factory =====
    class UserFactory {
        -verificationCode: String
        +createUser(userType, userName, password): User
    }

    class TheatreFactory {
        +createTheatre(user, theatreName): Theatre
    }

    %% ===== Strategy =====
    class PaymentStrategy {
        <<interface>>
        +pay(amount): boolean
    }

    class CreditCardPayment {
        -cardNumber: String
        -cardHolderName: String
        -expiryDate: String
        -cvv: String
        +pay(amount): boolean
    }

    class UpiPayment {
        -upiId: String
        +pay(amount): boolean
    }

    %% ===== Inventory / Repository =====
    class TheatreInventory {
        -registeredTheatresMap: Map~String, Theatre~
        -movieMap: Map~String, Movie~
        +addTheatre(theatre)
        +getTheatreById(theatreId): Theatre
        +getMovieByName(name): Movie
        +getTheatreByName(name): Theatre
        +addShowToTheatre(theatre, movie, show)
        +removeMovieFromTheatre(theatre, movieName)
        +removeShowFromTheatre(theatre, movieName, showId): boolean
        +updateSeatingForShow(theatre, movieName, showId, newSeats)
        +displayUpcomingShowsForTheatre(theatre)
        +displayAllUpcomingShows()
        +displayAllUpcomingMovies()
        +displayTheatresAndShowsForMovie(selection)
    }

    class UserInventory {
        -userMap: Map~String, User~
        +addUser(user)
        +getUserById(userId): User
        +verifyUserAsAdmin(user): boolean
    }

    %% ===== Enum Classes =====
    class UserType {
        <<enumeration>>
        ADMIN
        CUSTOMER
    }

    class SeatType {
        <<enumeration>>
        REGULAR
        PREMIUM
    }

    class SeatStatus {
        <<enumeration>>
        AVAILABLE
        HELD
        BOOKED
    }

    class PaymentStatus {
        <<enumeration>>
        PENDING
        SUCCESS
        FAILED
    }

    %% ===== Relationships =====
    TicketBookingSimulation --> TicketBookingController
    TicketBookingController --> TicketBookingService
    TicketBookingService --> UserInventory
    TicketBookingService --> TheatreInventory
    TicketBookingService --> BookingManager
    BookingManager --> TheatreInventory
    BookingManager --> Selection
    BookingManager --> PaymentProcessor
    TicketBookingService --> UserFactory
    TicketBookingService --> TheatreFactory

    TheatreInventory --> Theatre
    Theatre --> Movie
    Theatre --> Show
    Show --> Seat
    Booking --> User
    Booking --> Theatre
    Booking --> Movie
    Booking --> Show
    Booking --> Seat
    Booking --> Payment

    Payment --> PaymentStrategy
    CreditCardPayment --> PaymentStrategy
    UpiPayment --> PaymentStrategy

```


## Summary

- Model layer: `User`, `Theatre`, `Movie`, `Show`, `Seat`, `Booking`, `Payment`, `enums`. 
- Repository layer: `UserInventory`, `TheatreInventory`. 
- Service layer: `TicketBookingService` as a singleton facade and `PaymentProcessor`. 
- Controller layer: `TicketBookingController`, `BookingManager`. 
- Factory layer: `UserFactory`, `TheatreFactory`. 
- Strategy pattern for payment: `CreditCardPayment`, `UpiPayment`. 
- CLI simulation: `TicketBookingSimulation` class with admin and customer menus.