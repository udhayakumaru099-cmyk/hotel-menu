package com.hotel.emenu.component;

import com.hotel.emenu.model.Category;
import com.hotel.emenu.model.MenuItem;
import com.hotel.emenu.model.User;
import com.hotel.emenu.repository.CategoryRepository;
import com.hotel.emenu.repository.MenuItemRepository;
import com.hotel.emenu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final MenuItemRepository menuItemRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initializeUsers();
        // Clear and force update with unique images
        menuItemRepository.deleteAll();
        categoryRepository.deleteAll();
        initializeMenu();
    }

    private void initializeUsers() {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("System Administrator");
            admin.setRole(User.Role.ADMIN);
            admin.setEmail("admin@hotel.com");
            admin.setIsActive(true);
            userRepository.save(admin);

            User kitchen = new User();
            kitchen.setUsername("kitchen");
            kitchen.setPassword(passwordEncoder.encode("kitchen123"));
            kitchen.setFullName("Kitchen Staff");
            kitchen.setRole(User.Role.KITCHEN);
            kitchen.setEmail("kitchen@hotel.com");
            kitchen.setIsActive(true);
            userRepository.save(kitchen);

            System.out.println("Default Users created!");
        }
    }

    private void initializeMenu() {
        // Categories
        Category appetizers = createCategory("Appetizers", "Start your meal with these delicious starters", 1);
        Category mainCourse = createCategory("Main Course", "Hearty and fulfilling main dishes", 2);
        Category snacks = createCategory("Snacks", "Perfect for a quick bite", 3);
        Category desserts = createCategory("Desserts", "Sweet treats to end your meal", 4);
        Category beverages = createCategory("Beverages", "Refreshing drinks and coffees", 5);

        // Appetizers (10 items) - Unique Images
        createMenuItem(appetizers, "Paneer Tikka", "Spiced cottage cheese cubes grilled to perfection", 250, true, true,
                "https://images.unsplash.com/photo-1567188040759-fbba1883db6a?w=800");
        createMenuItem(appetizers, "Crispy Corn", "Golden fried corn kernels with spices", 180, true, false,
                "https://images.unsplash.com/photo-1514516322520-29928952573a?w=800");
        createMenuItem(appetizers, "Chicken 65", "Spicy deep-fried chicken bites", 320, false, true,
                "https://images.unsplash.com/photo-1610057099443-fde8c4d50f91?w=800");
        createMenuItem(appetizers, "Hara Bhara Kabab", "Healthy spinach and green pea kababs", 210, true, false,
                "https://images.unsplash.com/photo-1601050690597-df056fb46793?w=800");
        createMenuItem(appetizers, "Fish Amritsari", "Deep fried fish fillets with carom seeds", 380, false, true,
                "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?w=800");
        createMenuItem(appetizers, "Spring Rolls", "Vegetable filled crunchy rolls", 150, true, false,
                "https://images.unsplash.com/photo-1544124499-58ed63897ca4?w=800");
        createMenuItem(appetizers, "Chilli Mushroom", "Stir-fried mushrooms in spicy sauce", 240, true, true,
                "https://images.unsplash.com/photo-1600850056064-a8b380df8395?w=800");
        createMenuItem(appetizers, "Mutton Seekh Kabab", "Minced mutton skewers with herbs", 450, false, true,
                "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=800");
        createMenuItem(appetizers, "Veg Manchurian", "Veggie balls in soy-garlic sauce", 190, true, true,
                "https://images.unsplash.com/photo-1585032226651-759b368d7246?w=800");
        createMenuItem(appetizers, "Honey Chilli Potato", "Crispy potatoes in honey chilli sauce", 170, true, true,
                "https://images.unsplash.com/photo-1619895092538-1283417871fa?w=800");

        // Main Course (10 items) - Unique Images
        createMenuItem(mainCourse, "Butter Chicken", "Creamy tomato-based chicken curry", 480, false, false,
                "https://images.unsplash.com/photo-1603894584714-f4e92ea80893?w=800");
        createMenuItem(mainCourse, "Dal Makhani", "Slow-cooked black lentils", 350, true, false,
                "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=800");
        createMenuItem(mainCourse, "Mutton Rogan Josh", "Kashmiri style mutton curry", 550, false, true,
                "https://images.unsplash.com/photo-1541529086526-db283c563270?w=800");
        createMenuItem(mainCourse, "Paneer Butter Masala", "Cottage cheese in rich gravy", 390, true, false,
                "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=800");
        createMenuItem(mainCourse, "Chicken Biryani", "Fragrant rice with spiced chicken", 420, false, true,
                "https://images.unsplash.com/photo-1563379091339-03b21bc4a4f8?w=800");
        createMenuItem(mainCourse, "Veg Kolhapuri", "Extra spicy mixed vegetable curry", 340, true, true,
                "https://images.unsplash.com/photo-1613292443284-8d10efb839d6?w=800");
        createMenuItem(mainCourse, "Kadhai Paneer", "Paneer with bell peppers in spicy gravy", 410, true, true,
                "https://images.unsplash.com/photo-1596797038583-18a68a637311?w=800");
        createMenuItem(mainCourse, "Malai Kofta", "Potato and cheese dumplings in white gravy", 420, true, false,
                "https://images.unsplash.com/photo-1588166524941-3bf61a9c41db?w=800");
        createMenuItem(mainCourse, "Egg Curry", "Boiled eggs in spicy onion gravy", 280, false, true,
                "https://images.unsplash.com/photo-1604152135912-04a002e7a089?w=800");
        createMenuItem(mainCourse, "Fish Curry", "Traditional fish stew in coastal flavors", 490, false, true,
                "https://images.unsplash.com/photo-1534939561126-855b8675edd7?w=800");

        // Snacks (10 items) - Unique Images
        createMenuItem(snacks, "Samosa", "Classic potato filled pastry", 40, true, false,
                "https://images.unsplash.com/photo-1601050690597-df056fb46793?w=800");
        createMenuItem(snacks, "Vada Pav", "Mumbai style burger", 60, true, true,
                "https://images.unsplash.com/photo-1606491956689-2ea8c5388000?w=800");
        createMenuItem(snacks, "French Fries", "Salted crispy potato fries", 120, true, false,
                "https://images.unsplash.com/photo-1573082894257-2e2124508c2a?w=800");
        createMenuItem(snacks, "Cheese Garlic Bread", "Baked bread with cheese and garlic", 150, true, false,
                "https://images.unsplash.com/photo-1619096279114-42617fbc0bc0?w=800");
        createMenuItem(snacks, "Chicken Nuggets", "Breaded chicken bites", 220, false, false,
                "https://images.unsplash.com/photo-1562967914-6cbb04bac331?w=800");
        createMenuItem(snacks, "Onion Pakora", "Crispy deep fried onion fritters", 80, true, true,
                "https://images.unsplash.com/photo-1610444605995-125952d7168d?w=800");
        createMenuItem(snacks, "Aloo Tikki", "Fried potato patties with chutney", 90, true, true,
                "https://images.unsplash.com/photo-1606491956391-70533a191b3c?w=800");
        createMenuItem(snacks, "Nachos Pepper Salsa", "Tortilla chips with spicy salsa", 180, true, true,
                "https://images.unsplash.com/photo-1513456852971-30c0b8199d4c?w=800");
        createMenuItem(snacks, "Paneer Sandwich", "Grilled sandwich with paneer filling", 140, true, false,
                "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?w=800");
        createMenuItem(snacks, "Chicken Burger", "Juicy chicken patty with lettuce", 250, false, false,
                "https://images.unsplash.com/photo-1571091718767-18b5c1457add?w=800");

        // Desserts (10 items) - Unique Images
        createMenuItem(desserts, "Gulab Jamun", "Sweet milk solids in syrup", 120, true, false,
                "https://images.unsplash.com/photo-1605666804791-5374e2a6d713?w=800");
        createMenuItem(desserts, "Ras Malai", "Soft cheese patties in saffron milk", 150, true, false,
                "https://images.unsplash.com/photo-1541913759047-65825df599f6?w=800");
        createMenuItem(desserts, "Chocolate Lava Cake", "Warm chocolate center cake", 210, true, false,
                "https://images.unsplash.com/photo-1624353365286-3f8d62daad51?w=800");
        createMenuItem(desserts, "Vanilla Ice Cream", "Classic creamy vanilla", 90, true, false,
                "https://images.unsplash.com/photo-1501443762994-82bd5dace89a?w=800");
        createMenuItem(desserts, "Kulfi Falooda", "Traditional Indian frozen dessert", 140, true, false,
                "https://images.unsplash.com/photo-1517093602195-b40af9688b46?w=800");
        createMenuItem(desserts, "Gajar Ka Halwa", "Warm carrot pudding with nuts", 160, true, false,
                "https://images.unsplash.com/photo-1606626615714-3fb86e680a3a?w=800");
        createMenuItem(desserts, "Blueberry Cheesecake", "Creamy cheesecake with blueberry", 280, true, false,
                "https://images.unsplash.com/photo-1533134242443-d4fd215305ad?w=800");
        createMenuItem(desserts, "Tiramisu", "Coffee flavored Italian dessert", 320, true, false,
                "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=800");
        createMenuItem(desserts, "Brownie with Ice Cream", "Warm brownie with vanilla scoop", 240, true, false,
                "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=800");
        createMenuItem(desserts, "Apple Pie", "Classic apple and cinnamon pie", 190, true, false,
                "https://images.unsplash.com/photo-1568571780765-9276ac8b7da3?w=800");

        // Beverages (10 items) - Unique Images
        createMenuItem(beverages, "Masala Tea", "Spiced Indian tea", 40, true, false,
                "https://images.unsplash.com/photo-1544787210-221ca9424e8e?w=800");
        createMenuItem(beverages, "Cold Coffee", "Chilled milk and coffee blend", 150, true, false,
                "https://images.unsplash.com/photo-1572286258217-40142c1c6a70?w=800");
        createMenuItem(beverages, "Fresh Lime Soda", "Zesty and refreshing soda", 90, true, false,
                "https://images.unsplash.com/photo-1506159904226-00ea9039396e?w=800");
        createMenuItem(beverages, "Mango Lassi", "Yogurt based mango drink", 120, true, false,
                "https://images.unsplash.com/photo-1620980756314-87cc9c323f4c?w=800");
        createMenuItem(beverages, "Virgin Mojito", "Mint and lime mocktail", 180, true, false,
                "https://images.unsplash.com/photo-1551024709-8f23befc6f87?w=800");
        createMenuItem(beverages, "Hot Chocolate", "Rich creamy cocoa drink", 160, true, false,
                "https://images.unsplash.com/photo-1544145945-f904253db0ad?w=800");
        createMenuItem(beverages, "Iced Tea", "Refreshing lemon flavored tea", 110, true, false,
                "https://images.unsplash.com/photo-1556679343-c7306c1976bc?w=800");
        createMenuItem(beverages, "Watermelon Juice", "Freshly squeezed watermelon", 130, true, false,
                "https://images.unsplash.com/photo-1571767454098-246b94dbbc70?w=800");
        createMenuItem(beverages, "Coca Cola", "300ml soft drink", 60, true, false,
                "https://images.unsplash.com/photo-1622483767028-3f66f3614051?w=800");
        createMenuItem(beverages, "Cappuccino", "Classic Italian coffee", 140, true, false,
                "https://images.unsplash.com/photo-1572442388796-11668a07c138?w=800");

        System.out.println("Menu Database Initialized with 50 items and unique images!");
    }

    private Category createCategory(String name, String desc, int order) {
        Category cat = new Category();
        cat.setCategoryName(name);
        cat.setDescription(desc);
        cat.setDisplayOrder(order);
        cat.setIsActive(true);
        return categoryRepository.save(cat);
    }

    private void createMenuItem(Category cat, String name, String desc, double price, boolean isVeg, boolean isSpicy,
            String imgUrl) {
        MenuItem item = new MenuItem();
        item.setCategory(cat);
        item.setItemName(name);
        item.setDescription(desc);
        item.setPrice(BigDecimal.valueOf(price));
        item.setIsVegetarian(isVeg);
        item.setIsSpicy(isSpicy);
        item.setIsAvailable(true);
        item.setImageUrl(imgUrl);
        menuItemRepository.save(item);
    }
}
