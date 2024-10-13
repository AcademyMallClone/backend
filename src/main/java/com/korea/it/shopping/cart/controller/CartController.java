package com.korea.it.shopping.cart.controller;

import com.korea.it.shopping.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    // 생성자 의존성 주입
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 상품 장바구니에 추가
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        try {
            String message = cartService.addToCart(productId, quantity);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니에 상품을 추가하는 중 오류가 발생했습니다.");
        }
    }

    // 다른 필요한 API 추가 가능
}
