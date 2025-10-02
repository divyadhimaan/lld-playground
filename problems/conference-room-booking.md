# Conference Room Booking System

Task is to build a simple Conference Room Booking System for an office environment. The system should allow employees to efficiently book available conference rooms for meetings, manage existing bookings, and ensure optimal utilization of the meeting spaces. The goal is to streamline the booking process and prevent scheduling conflicts.
Features:
- **Register Rooms**: Administrators can register conference rooms with their names, capacity (number of people it can accommodate), and available time slots. (Consider 2 types of rooms for now: Small (capacity 5-10) and Large (capacity 11-30), and slots as numbers from 1 to 10, representing hourly blocks from 9 AM to 6 PM).
- **Register Employees**: Employees can register themselves with their names and department.
- **Book Room**: Employees should be able to book a conference room for a specific time slot and duration, specifying the number of attendees. The system should automatically find an available room that meets the capacity requirements.
  If a booking is made for 20 min , room will be booked for 1 hr.
- **View Room Schedule**: Any user should be able to view the schedule for a specific room, showing all its booked slots and the employee who booked it.
- **View Employee Bookings**: Employees should be able to view all their own upcoming bookings.

### Requirements:
- `RegisterRoom()`
- `RegisterEmployee()`
- `BookRoom()`
- `ViewSchedule()` // This single requirement encompasses viewing both room and employee schedules. 


[Java Implementation](./../code/src/conferenceRoomBooking/RoomBookingSystemSimulation.java) | [Design Explanation](./../code/src/conferenceRoomBooking/RoomBookingSystem.md)