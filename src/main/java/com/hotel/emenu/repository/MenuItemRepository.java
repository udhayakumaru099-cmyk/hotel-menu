package com.hotel.emenu.repository;

import com.hotel.emenu.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    
    List<MenuItem> findByIsAvailableTrue();
    
    List<MenuItem> findByCategoryCategoryIdAndIsAvailableTrue(Long categoryId);
    
    List<MenuItem> findByIsVegetarianTrueAndIsAvailableTrue();
    
    List<MenuItem> findByIsSpicyTrueAndIsAvailableTrue();
    
    @Query("SELECT m FROM MenuItem m WHERE m.isAvailable = true AND " +
           "(LOWER(m.itemName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(m.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<MenuItem> searchByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT m FROM MenuItem m JOIN m.category c WHERE m.isAvailable = true " +
           "ORDER BY c.displayOrder ASC, m.itemName ASC")
    List<MenuItem> findAllAvailableOrderedByCategory();
}
