package com.korea.it.shopping.product.repo;

import com.korea.it.shopping.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
