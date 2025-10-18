# Design a Movie Ticket Booking System: BookMyShow

## Requirements

1. The system should allow users to view the list of movies playing in different theaters. 
2. Users should be able to select a movie, theater, and show timing to book tickets. 
3. The system should display the seating arrangement of the selected show and allow users to choose seats. 
4. Users should be able to make payments and confirm their booking. 
5. The system should handle concurrent bookings and ensure seat availability is updated in real-time. 
6. The system should support different types of seats (e.g., normal, premium) and pricing. 
7. The system should allow theater administrators to add, update, and remove movies, shows, and seating arrangements. 
8. The system should be scalable to handle a large number of concurrent users and bookings.


## Summary

- Model layer: `User`, `Theatre`, `Movie`, `Show`, `Seat`, `Booking`, `Payment`, `enums`.
- Repository layer: `UserInventory`, `TheatreInventory`.
- Service layer: `TicketBookingService` as a singleton facade and `PaymentProcessor`.
- Controller layer: `TicketBookingController`, `BookingManager`.
- Factory layer: `UserFactory`, `TheatreFactory`.
- Strategy pattern for payment: `CreditCardPayment`, `UpiPayment`.
- CLI simulation: `TicketBookingSimulation` class with admin and customer menus.

[Java Implementation](./../code/src/movieTicketBooking/app/TicketBookingSimulation.java) | [Design Explanation](./../code/src/movieTicketBooking/movieTicketBooking.md)

