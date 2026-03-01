# Hotel E-Menu System - Java Spring Boot Backend

A complete Java Spring Boot backend for the Hotel E-Menu and Online Ordering System using MVC architecture, JPA/Hibernate, and MySQL.

## Technology Stack

- **Java**: 17+
- **Framework**: Spring Boot 3.2.0
- **ORM**: Spring Data JPA / Hibernate
- **Database**: MySQL 8.0+
- **Security**: Spring Security
- **Template Engine**: Thymeleaf (for server-side rendering)
- **Build Tool**: Maven
- **API**: RESTful endpoints

## Project Structure

```
hotel-emenu-java/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── hotel/
│   │   │           └── emenu/
│   │   │               ├── HotelEmenuApplication.java
│   │   │               ├── model/              # JPA Entities
│   │   │               │   ├── User.java
│   │   │               │   ├── Category.java
│   │   │               │   ├── MenuItem.java
│   │   │               │   ├── Order.java
│   │   │               │   ├── OrderItem.java
│   │   │               │   └── Bill.java
│   │   │               ├── repository/         # Spring Data JPA
│   │   │               │   ├── UserRepository.java
│   │   │               │   ├── CategoryRepository.java
│   │   │               │   ├── MenuItemRepository.java
│   │   │               │   ├── OrderRepository.java
│   │   │               │   └── BillRepository.java
│   │   │               ├── service/            # Business Logic
│   │   │               │   ├── UserService.java
│   │   │               │   ├── MenuService.java
│   │   │               │   ├── OrderService.java
│   │   │               │   └── BillService.java
│   │   │               ├── controller/         # REST Controllers
│   │   │               │   ├── MenuController.java
│   │   │               │   ├── OrderController.java
│   │   │               │   ├── AdminController.java
│   │   │               │   └── KitchenController.java
│   │   │               ├── config/             # Configuration
│   │   │               │   ├── SecurityConfig.java
│   │   │               │   └── WebConfig.java
│   │   │               └── dto/                # Data Transfer Objects
│   │   │                   ├── OrderRequest.java
│   │   │                   └── OrderResponse.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── templates/                      # Thymeleaf templates
│   │       └── static/                         # CSS, JS, Images
│   └── test/
│       └── java/
├── pom.xml                                      # Maven dependencies
└── README.md
```

## Features

### MVC Architecture
- **Model**: JPA entities with relationships
- **View**: Thymeleaf templates or RESTful JSON responses
- **Controller**: Request handling and routing

### JPA Entities

1. **User** - Authentication (Admin, Kitchen staff)
2. **Category** - Menu categories
3. **MenuItem** - Menu items with details
4. **Order** - Customer orders
5. **OrderItem** - Order line items
6. **Bill** - Generated invoices

### Key Features
- RESTful API endpoints
- Spring Security authentication
- JPA/Hibernate ORM
- Transaction management
- Bean validation
- File upload support
- Session management

## Prerequisites

- JDK 17 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, or VS Code with Java extensions)

## Installation & Setup

### 1. Install Java JDK
Download and install Java 17+ from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)

```bash
# Verify installation
java -version
```

### 2. Install Maven
Download from [Apache Maven](https://maven.apache.org/download.cgi)

```bash
# Verify installation
mvn -version
```

### 3. Setup MySQL Database
Create the database (use the same database.sql from the PHP version):

```bash
mysql -u root -p < ../hotel-emenu/database.sql
```

Or manually:
```sql
CREATE DATABASE hotel_emenu;
USE hotel_emenu;
-- Run the SQL schema from database.sql
```

### 4. Configure Application
Edit `src/main/resources/application.properties`:

```properties
# Update these if needed
spring.datasource.url=jdbc:mysql://localhost:3306/hotel_emenu
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### 5. Build the Project

```bash
cd hotel-emenu-java
mvn clean install
```

### 6. Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR file:
```bash
java -jar target/emenu-1.0.0.jar
```

The application will start on `http://localhost:8080`

## API Endpoints

### Public Endpoints

#### Menu
- `GET /api/menu` - Get all menu items
- `GET /api/menu/category/{id}` - Get items by category
- `GET /api/categories` - Get all categories
- `GET /api/menu/{id}` - Get single menu item

#### Orders
- `POST /api/orders` - Place new order
- `GET /api/orders/{orderNumber}` - Track order status
- `GET /api/orders/{orderNumber}/bill` - Get bill

### Admin Endpoints (Requires Authentication)

#### Menu Management
- `POST /api/admin/menu` - Add menu item
- `PUT /api/admin/menu/{id}` - Update menu item
- `DELETE /api/admin/menu/{id}` - Delete menu item
- `POST /api/admin/menu/{id}/upload` - Upload image

#### Category Management
- `POST /api/admin/categories` - Add category
- `PUT /api/admin/categories/{id}` - Update category
- `DELETE /api/admin/categories/{id}` - Delete category

#### Order Management
- `GET /api/admin/orders` - Get all orders
- `PUT /api/admin/orders/{id}/status` - Update order status

### Kitchen Endpoints (Requires Authentication)

- `GET /api/kitchen/orders/active` - Get active orders
- `PUT /api/kitchen/orders/{id}/status` - Update order status

## Spring Boot Features Used

### 1. Spring Data JPA
- Automatic CRUD operations
- Custom query methods
- Relationship mapping (OneToMany, ManyToOne, OneToOne)

### 2. Spring Security
- Password encoding (BCrypt)
- Role-based access control
- Session management
- CSRF protection

### 3. Spring MVC
- RESTful controllers
- Request/Response DTOs
- Exception handling
- Validation

### 4. Spring Boot Actuator (Optional)
Add to monitor application health:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

## Configuration

### Database Configuration
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Options for `ddl-auto`:
- `create` - Drop and recreate tables (⚠️ Data loss)
- `update` - Update schema (Recommended for development)
- `validate` - Validate schema only
- `none` - No schema management

### Logging
```properties
logging.level.com.hotel.emenu=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

## Running Tests

```bash
mvn test
```

## Building for Production

1. Update `application.properties`:
```properties
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
```

2. Build JAR:
```bash
mvn clean package
```

3. Run:
```bash
java -jar target/emenu-1.0.0.jar
```

## Comparison: PHP vs Java Spring Boot

### PHP Version
- ✅ Simple setup (XAMPP)
- ✅ Quick development
- ✅ Lower learning curve
- ❌ Less structured
- ❌ Manual ORM

### Java Spring Boot Version
- ✅ Enterprise-grade
- ✅ Strong typing
- ✅ Auto CRUD with JPA
- ✅ Built-in security
- ✅ Better scalability
- ✅ Comprehensive testing
- ❌ Steeper learning curve
- ❌ More configuration

## IDE Setup

### IntelliJ IDEA (Recommended)
1. File → Open → Select `pom.xml`
2. Enable Maven auto-import
3. Run `HotelEmenuApplication.java`

### Eclipse
1. File → Import → Maven → Existing Maven Projects
2. Select project directory
3. Run as Spring Boot App

### VS Code
1. Install Java Extension Pack
2. Install Spring Boot Extension Pack
3. Open folder
4. Run from Spring Boot Dashboard

## Troubleshooting

### Port Already in Use
Change port in `application.properties`:
```properties
server.port=8081
```

### Database Connection Error
- Verify MySQL is running
- Check credentials in `application.properties`
- Ensure database exists

### Lombok Not Working
- Install Lombok plugin for your IDE
- Enable annotation processing

### Build Fails
```bash
mvn clean install -U
```

## Development Tips

1. **Hot Reload**: Spring Boot DevTools enables automatic restart
2. **Database GUI**: Use MySQL Workbench or DBeaver
3. **API Testing**: Use Postman or curl
4. **Debugging**: Set breakpoints in IDE

## Next Steps

To complete the Java backend:

1. **Implement Services** - Business logic layer
2. **Create Controllers** - REST endpoints
3. **Add Security Config** - Authentication setup
4. **Write Tests** - Unit and integration tests
5. **Add DTOs** - Request/Response objects
6. **Frontend Integration** - Connect with existing HTML/CSS/JS

## Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [Hibernate ORM](https://hibernate.org/orm/documentation/)

## License

Educational project for learning Spring Boot and MVC architecture.

---

**Version**: 1.0.0  
**Framework**: Spring Boot 3.2.0  
**Java**: 17+
