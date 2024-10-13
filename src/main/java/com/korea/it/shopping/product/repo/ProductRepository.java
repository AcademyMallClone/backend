package com.korea.it.shopping.product.repo;

import com.korea.it.shopping.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductNameContainingIgnoreCase(String query); // 대소문자 구분 없이 이름에 검색어가 포함된 상품 검색
    Optional<Product> findByProductId(Integer id);
}
