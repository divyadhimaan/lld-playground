# Solution - Linkedin Verdict

The LinkedIn Verdict System follows multiple Low-Level Design (LLD) best practices and design patterns to ensure scalability, maintainability, and clean architecture. 

## Components

### 1. VerdictController
- Role: Handles HTTP requests for the /verdicts endpoint.
- Functions:
  - `POST /verdicts`: Accepts a VerdictRequest and delegates to VerdictService to create a new Verdict.
  - `GET /verdicts`: Retrieves a list of public Verdict objects for a specified targetId.
  - `POST /verdicts/{verdictId}/like`: Likes a Verdict by incrementing its likeCount.
  - `POST /verdicts/{verdictId}/report`: Reports a Verdict by incrementing its reportCount.
- Dependencies:
  Depends on `VerdictService` to perform business logic and data manipulation.

### 2. VerdictService
- Role: Contains the business logic for handling Verdict objects.
- Functions:
  - `addVerdict()`: Calls the VerdictFactory to create a new Verdict and adds it to an in-memory store (verdicts).
  - `getVerdicts()`: Retrieves a list of public Verdict objects for a specified targetId.
  - `likeVerdict()`: Increments the likeCount for a specified Verdict.
  - `reportVerdict()`: Increments the reportCount for a specified Verdict.
- Dependencies:
  - Relies on the `VerdictFactory` to create new Verdict objects.
  - Uses an in-memory Map<String, Verdict> for storing the verdicts.

### 3. VerdictFactory
- Role: Creates instances of the Verdict class. It is used by VerdictService for object creation.
- Functions:
  - `createVerdict()`: Instantiates a Verdict object and assigns a unique ID.
- Dependencies:
  None: It's a utility class that provides object creation logic.

### 4. Verdict (Model)
- Role: Represents the data structure for a Verdict. This class holds attributes like authorId, targetId, rating, likeCount, etc.
- Functions:
  - Is declared with `@Data` `@NoArgsConstructor` `@AllArgsConstructor` -> adds getter/setters automatically.
  - Holds attributes of a Verdict (ID, content, likes, reports, etc.).
  - Provides constructors to initialize new Verdict objects.
- Dependencies:
  None: It is a simple POJO (Plain Old Java Object) representing a Verdict.

### 5. VerdictRequest (Model)
- Role: Represents the input data structure for creating a new Verdict through an API request.
- Functions:
  - Holds the necessary fields for creating a new Verdict (authorId, targetId, rating, etc.).
- Dependencies:
  None: Simple POJO used for API input.


## Design Patterns followed:

### 1. Factory Pattern:
- **Why?** 
  - Instead of constructing `Verdict` objects everywhere, a centralized method ensures consistent object creation.
  - Ensures consistency in `Verdict` creation.
  - Easy to modify in the future (e.g., add more fields).
- **Usage:** A dedicated `VerdictFactory` class for better encapsulation.
- `VerdictFactory` is injected into the `VerdictService`
### 2. Singleton:
- **Why?** A single instance of `VerdictService` avoids redundant memory allocation.
- **Usage:** `VerdictService` is a singleton managed by Spring (`@Service` ensures only one instance exists).
- `@Service` makes it a singleton automatically.
### 3. Repository:
- **Why?** Decouples business logic from data access.
- **Improvement?** Introduce `VerdictRepository` if using a database.
- **Usage:** Even though we use an in-memory HashMap now, the service layer abstracts data storage, making it easy to switch to a database later.
### 4. Controller-Service Pattern:
- **Why?** Keeps the code clean, modular, and testable.
- The controller only handles HTTP requests, while business logic is in the service layer.
### 5. DTO Pattern (Data Transfer Object)
- **Why?** Prevents exposing internal entity structures in API responses.
- **Usage:** `VerdictRequest` is a DTO to separate API models from internal models.


## Future Enhancements
1. Observer Pattern (for Like & Report Feature)
   - **Why?** Decouples event producers from consumers, making the system scalable.
   - **Usage:** If we want real-time notifications when a verdict is liked or reported, we can use an event-driven approach.

