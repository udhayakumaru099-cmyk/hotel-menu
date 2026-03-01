package com.hotel.emenu.service;

import com.hotel.emenu.model.Category;
import com.hotel.emenu.model.MenuItem;
import com.hotel.emenu.repository.CategoryRepository;
import com.hotel.emenu.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;

    private static final String UPLOAD_DIR = "uploads/menu-images/";

    // Category operations
    public List<Category> getAllCategories() {
        return categoryRepository.findAllByOrderByDisplayOrderAsc();
    }

    public List<Category> getActiveCategories() {
        return categoryRepository.findByIsActiveTrueOrderByDisplayOrderAsc();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    public Category createCategory(Category category) {
        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new RuntimeException("Category already exists: " + category.getCategoryName());
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category category) {
        Category existing = getCategoryById(id);
        existing.setCategoryName(category.getCategoryName());
        existing.setDescription(category.getDescription());
        existing.setDisplayOrder(category.getDisplayOrder());
        existing.setIsActive(category.getIsActive());
        return categoryRepository.save(existing);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    // Menu Item operations
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> getAvailableMenuItems() {
        return menuItemRepository.findAllAvailableOrderedByCategory();
    }

    public List<MenuItem> getMenuItemsByCategory(Long categoryId) {
        return menuItemRepository.findByCategoryCategoryIdAndIsAvailableTrue(categoryId);
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id));
    }

    public List<MenuItem> searchMenuItems(String keyword) {
        return menuItemRepository.searchByKeyword(keyword);
    }

    public List<MenuItem> getVegetarianItems() {
        return menuItemRepository.findByIsVegetarianTrueAndIsAvailableTrue();
    }

    public List<MenuItem> getSpicyItems() {
        return menuItemRepository.findByIsSpicyTrueAndIsAvailableTrue();
    }

    public MenuItem createMenuItem(MenuItem menuItem) {
        Category category = getCategoryById(menuItem.getCategory().getCategoryId());
        menuItem.setCategory(category);
        return menuItemRepository.save(menuItem);
    }

    public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
        MenuItem existing = getMenuItemById(id);
        existing.setItemName(menuItem.getItemName());
        existing.setDescription(menuItem.getDescription());
        existing.setPrice(menuItem.getPrice());
        existing.setCategory(menuItem.getCategory());
        existing.setIsAvailable(menuItem.getIsAvailable());
        existing.setIsVegetarian(menuItem.getIsVegetarian());
        existing.setIsSpicy(menuItem.getIsSpicy());
        existing.setPreparationTime(menuItem.getPreparationTime());
        return menuItemRepository.save(existing);
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }

    public String uploadMenuItemImage(Long itemId, MultipartFile file) throws IOException {
        MenuItem menuItem = getMenuItemById(itemId);

        // Validate file
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = "item_" + UUID.randomUUID().toString() + extension;

        // Create upload directory if not exists
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save file
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Update menu item
        menuItem.setImageUrl(filename);
        menuItemRepository.save(menuItem);

        return filename;
    }
}
