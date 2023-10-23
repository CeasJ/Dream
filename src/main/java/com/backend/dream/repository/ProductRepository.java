package com.backend.dream.repository;

import com.backend.dream.dto.ProductDTO;
import com.backend.dream.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE ?1")
    List<Product> findByName(String name);

    @Query("SELECT p FROM Product p where category.id=?1")
    Page<Product> findByCategoryID(Long categoryId, Pageable pageable);


    // For Sort products function
    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 ORDER BY p.price ASC")
    Page<Product> findByCategoryOrderByPriceAsc(Long categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 ORDER BY p.price DESC")
    Page<Product> findByCategoryOrderByPriceDesc(Long categoryId, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String productName, Pageable pageable);

    // Discount products
    @Query("SELECT p FROM Product p JOIN Discount d ON p.id = d.product.id WHERE d.activeDate <= current_date AND d.expiredDate >= current_date")
    Page<Product> findSaleProducts(Pageable pageable);





}
