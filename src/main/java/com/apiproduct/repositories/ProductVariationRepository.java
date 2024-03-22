package com.apiproduct.repositories;

import com.apiproduct.entities.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {

    @Query("select pv from ProductVariation pv where pv.product.id = :productId and pv.id = :productVariationId")
    Optional<com.apiproduct.repositories.ProductVariationRepository> findByProductIdAdProductVariationId(@Param("productId") Long productId, @Param("productVariationId") Long productVariationId);

}


