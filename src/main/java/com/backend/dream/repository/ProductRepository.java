package com.backend.dream.repository;

import com.backend.dream.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE ?1")
    List<Product> findByName(String name);

    @Query("SELECT p FROM Product p where category.id=?1")
    List<Product> findByCategoryID(Long categoryId);

    // For Sort products function
    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 ORDER BY p.price ASC")
    List<Product> findByCategoryOrderByPriceAsc(Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 ORDER BY p.price DESC")
    List<Product> findByCategoryOrderByPriceDesc(Long categoryId);

    List<Product> findByNameContainingIgnoreCase(String productName);

}
