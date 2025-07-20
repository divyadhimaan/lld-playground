# Elevator System

1. The elevator system should consist of multiple elevators serving multiple floors. 
2. Each elevator should have a capacity limit and should not exceed it. 
3. Users should be able to request an elevator from any floor and select a destination floor. 
4. The elevator system should efficiently handle user requests and optimize the movement of elevators to minimize waiting time. 
5. The system should prioritize requests based on the direction of travel and the proximity of the elevators to the requested floor. 
6. The elevators should be able to handle multiple requests concurrently and process them in an optimal order. 
7. The system should ensure thread safety and prevent race conditions when multiple threads interact with the elevators.


- This solution is still missing threading and concurrent requests. Only handle single user requests.
[Java Implementation](./../code/src/elevatorSystem/execution/ElevatorSystem.java)