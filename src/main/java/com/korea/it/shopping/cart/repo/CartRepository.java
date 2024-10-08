package com.korea.it.shopping.cart.repo;

import com.korea.it.shopping.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}