package com.backend.dream.repository;

import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize,Long> {
    @Query("SELECT ps FROM ProductSize ps WHERE ps.product.id = :productId")
    List<ProductSize> findAllByProductId(Long productId);

}
