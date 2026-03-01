# Quick Start Guide - Hotel E-Menu Java Backend

## Prerequisites Check

```bash
# Check Java (need 17+)
java -version

# Check Maven
mvn -version

# Check MySQL
mysql --version
```

## Setup Steps

### 1. Create Database
```bash
# Login to MySQL
mysql -u root -p

# Create database
CREATE DATABASE hotel_emenu;

# Use the database.sql from the PHP version
mysql -u root -p hotel_emenu < ../hotel-emenu/database.sql
```

### 2. Configure Application
Edit `src/main/resources/application.properties` if needed:
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 3. Build Project
```bash
cd C:\Users\udhay\hotel-emenu-java
mvn clean install
```

### 4. Run Application
```bash
mvn spring-boot:run
```

Or run the JAR:
```bash
java -jar target/emenu-1.0.0.jar
```

## Test the API

### Public Endpoints (No Authentication)

**Get all menu items:**
```bash
curl http://localhost:8080/api/menu
```

**Get categories:**
```bash
curl http://localhost:8080/api/menu/categories
```

**Place an order:**
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "John Doe",
    "customerPhone": "1234567890",
    "customerEmail": "john@example.com",
    "tableNumber": "T1",
    "items": [
      {
        "menuItem": {"itemId": 1},
        "quantity": 2
      }
    ]
  }'
```

**Track order:**
```bash
curl http://localhost:8080/api/orders/ORD20260301XXXX
```

### Admin Endpoints (Requires Authentication)

**Login and get menu items:**
```bash
curl -u admin:admin123 http://localhost:8080/api/admin/menu
```

**Create menu item:**
```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/admin/menu \
  -H "Content-Type: application/json" \
  -d '{
    "itemName": "New Item",
    "description": "Delicious food",
    "price": 15.99,
    "category": {"categoryId": 1},
    "isAvailable": true
  }'
```

**Dashboard stats:**
```bash
curl -u admin:admin123 http://localhost:8080/api/admin/dashboard/stats
```

### Kitchen Endpoints (Requires Authentication)

**Get active orders:**
```bash
curl -u kitchen1:admin123 http://localhost:8080/api/kitchen/orders/active
```

**Update order status:**
```bash
curl -u kitchen1:admin123 -X PUT \
  "http://localhost:8080/api/kitchen/orders/1/status?status=preparing"
```

## Default Credentials

- **Admin**: username: `admin`, password: `admin123`
- **Kitchen**: username: `kitchen1`, password: `admin123`

## Testing with Postman

1. Open Postman
2. Import collection or create new requests
3. Set Authorization → Basic Auth
4. Username: `admin`, Password: `admin123`
5. Test endpoints

## Common Issues

### Port Already in Use
```properties
# Change in application.properties
server.port=8081
```

### Database Connection Error
- Verify MySQL is running
- Check credentials in application.properties
- Ensure database exists

### Lombok Errors
- Install Lombok plugin in your IDE
- Enable annotation processing

### Build Fails
```bash
mvn clean install -U
```

## Project Structure

```
src/main/java/com/hotel/emenu/
├── HotelEmenuApplication.java    # Main class
├── model/                         # JPA Entities
├── repository/                    # Data access
├── service/                       # Business logic
├── controller/                    # REST APIs
├── config/                        # Configuration
└── dto/                          # Data Transfer Objects
```

## API Endpoints Summary

### Public
- `GET /api/menu` - All menu items
- `GET /api/menu/categories` - All categories
- `POST /api/orders` - Place order
- `GET /api/orders/{orderNumber}` - Track order

### Admin (ROLE_ADMIN)
- `POST /api/admin/menu` - Create item
- `PUT /api/admin/menu/{id}` - Update item
- `DELETE /api/admin/menu/{id}` - Delete item
- `GET /api/admin/orders` - All orders
- `GET /api/admin/dashboard/stats` - Statistics

### Kitchen (ROLE_KITCHEN)
- `GET /api/kitchen/orders/active` - Active orders
- `PUT /api/kitchen/orders/{id}/status` - Update status

## Next Steps

1. Connect existing frontend (HTML/CSS/JS) to REST API
2. Add more validation
3. Implement proper error handling
4. Add unit tests
5. Configure for production deployment

## IDE Support

### IntelliJ IDEA
1. File → Open → Select pom.xml
2. Wait for Maven import
3. Run HotelEmenuApplication

### VS Code
1. Install Java Extension Pack
2. Install Spring Boot Extension Pack
3. Open folder
4. Run from Spring Boot Dashboard

## Need Help?

Check the main README.md for detailed documentation.
