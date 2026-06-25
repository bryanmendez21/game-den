# 🎮 Game Den - E-Commerce API

Game Den is a full-stack e-commerce web application for browsing and purchasing video games and electronics. Built as a Spring Boot REST API with a MySQL database and a vanilla JavaScript frontend.

## 📸 Screenshots
<img width="1274" height="679" alt="Screenshot 2026-06-25 163435" src="https://github.com/user-attachments/assets/add91736-a477-4419-80ed-0bc8c651bf91" />
<img width="1277" height="668" alt="Screenshot 2026-06-25 164255" src="https://github.com/user-attachments/assets/a48b3c72-b1ed-40b8-b952-de142201952b" />
<img width="1277" height="677" alt="Screenshot 2026-06-25 164313" src="https://github.com/user-attachments/assets/fb5f26f5-ccf8-4f95-ad36-f9e5cbef03da" />


## 🛠️ Technologies Used
- Java 17
- Spring Boot
- Spring Security (JWT Authentication)
- Spring Data JPA / Hibernate
- MySQL
- Maven

## 🚀 Getting Started

### Prerequisites
- Java 17+
- MySQL
- Maven

### Database Setup
1. Run the provided SQL script in MySQL Workbench to create the database and seed data
2. Update `application.properties` with your credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/videogamestore
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Application
```bash
mvn spring-boot:run
```
API runs at `http://localhost:8080`

### Demo Users
| Username | Password | Role |
|----------|----------|------|
| user | password | USER |
| admin | password | ADMIN |
| george | password | USER |

## 📡 API Endpoints

### Authentication
| Method | URL | Description |
|--------|-----|-------------|
| POST | /register | Register new user |
| POST | /login | Login and receive JWT token |

### Categories
| Method | URL | Auth |
|--------|-----|------|
| GET | /categories | Public |
| GET | /categories/{id} | Public |
| GET | /categories/{id}/products | Public |
| POST | /categories | ADMIN |
| PUT | /categories/{id} | ADMIN |
| DELETE | /categories/{id} | ADMIN |

### Products
| Method | URL | Auth |
|--------|-----|------|
| GET | /products | Public |
| GET | /products/{id} | Public |
| POST | /products | ADMIN |
| PUT | /products/{id} | ADMIN |
| DELETE | /products/{id} | ADMIN |

### Shopping Cart
| Method | URL | Auth |
|--------|-----|------|
| GET | /cart | USER |
| POST | /cart/products/{id} | USER |
| PUT | /cart/products/{id} | USER |
| DELETE | /cart | USER |

### Profile
| Method | URL | Auth |
|--------|-----|------|
| GET | /profile | USER |
| PUT | /profile | USER |

### Orders
| Method | URL | Auth |
|--------|-----|------|
| POST | /orders | USER |

## 🔐 Authentication
All protected endpoints require a JWT token in the Authorization header:
