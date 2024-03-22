package com.apiproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface Product {
    @Repository
    public interface ProductRepository extends JpaRepository<Product, Long> {
    }
}
