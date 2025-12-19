# 🐦 Twitter Api - Full Stack Project

A full-stack social media application developed to simulate core Twitter functionalities. This project demonstrates a robust **N-Layer Architecture**, **RESTful API** design, and **Modern Frontend** integration.

## 🚀 Project Overview

This project connects a **Java Spring Boot** backend with a **React** frontend. It focuses on clean code principles, secure data handling, and comprehensive unit testing. The application handles complex business logic such as interaction permissions (who can delete a comment) and prevents duplicate interactions (likes/retweets).

## 🛠 Tech Stack

### Backend
* **Framework:** Java 17+, Spring Boot 3.x
* **Database:** PostgreSQL, Hibernate (Spring Data JPA)
* **Security:** Spring Security (PasswordEncoder)
* **Tools:** Lombok, Maven
* **Testing:** JUnit 5, Mockito (using `MockitoBean`), Spring Boot Test (MockMvc)

### Frontend
* **Library:** React.js (Hooks: `useState`, `useEffect`)
* **HTTP Client:** Axios (Centralized API management)
* **Styling:** CSS3

---

## ✨ Key Features & Business Logic

### 🔐 User Authentication
* User Registration and Login functionality.
* Password encryption using Spring Security.
* *Code Highlight:* `UserServiceImpl` handles secure registration and login validation.

### 📝 Tweet Management
* Create, Read (by User or ID), Update, and Delete Tweets.
* **Logic:** Only the owner of the tweet can update or delete it.

### 💬 Comment System (Smart Permissions)
* Users can comment on tweets.
* **Complex Logic:** A comment can be deleted by **either** the comment author **OR** the tweet owner.
* *Code Reference:* `CommentServiceImpl.java` implements this permission check.

### ❤️ Interactions (Like & Retweet)
* **Validation:** The system checks if a user has already liked or retweeted a specific tweet using `isPresent()` before saving to the database to prevent duplicates.
* Custom Exception Handling (`TwitterException`) returns meaningful HTTP status codes (404, 403, 400) instead of generic server errors.

---

## 🏗 Architecture

The project follows the **Controller-Service-Repository** pattern to ensure Separation of Concerns:

1.  **Controller Layer:** Handles HTTP requests and responses.
2.  **Service Layer:** Contains all business logic and validations.
3.  **Repository Layer:** Manages data access/database interactions.
4.  **Exception Handling:** A global exception handling mechanism maps internal errors to user-friendly HTTP responses.

---

## 🧪 Testing

Unit tests focus on the Controller layer to ensure API endpoints behave as expected.
* **Tools:** `MockMvc` and `Mockito`.
* **Approach:** Service layer dependencies are mocked using the modern `@MockitoBean` annotation to isolate the Controller tests.
* **Coverage:** Tests cover success scenarios (200 OK) and error handling for Login, Register, Tweet creation, and Comment management.

---

## 💻 Getting Started

### Prerequisites
* Java 17 or higher
* Node.js & npm
* PostgreSQL

### Backend Setup
1.  Clone the repository.
2.  Update `application.properties` with your PostgreSQL credentials.
3.  Run the application:
    ```bash
    mvn spring-boot:run
    ```

### Frontend Setup
1.  Navigate to the frontend folder.
2.  Install dependencies:
    ```bash
    npm install
    ```
3.  Start the React app:
    ```bash
    npm start
    ```

---

## 📸 API Usage Examples

The frontend uses a centralized `api.js` file for clean Axios implementation:

```javascript
// Example from src/api.js
export const api = {
    login: (email, password) => axios.post(`${API_URL}/auth/login`, { email, password }),
    createTweet: (userId, tweetDesc) => axios.post(`${API_URL}/tweet`, { userId, tweetDesc }),
    // ...
};