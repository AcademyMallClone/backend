package com.korea.it.shopping.cart.service;

import com.korea.it.shopping.cart.entity.Cart;
import com.korea.it.shopping.cart.repo.CartRepository;
import com.korea.it.shopping.product.entity.Product;
import com.korea.it.shopping.product.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    // UserRepository를 사용하지 않으므로 삭제
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // 장바구니에 상품 추가
    public String addToCart(Long productId, int quantity) {
        Optional<Product> product = productRepository.findById(productId);

        if (!product.isPresent()) {
            return "해당 상품을 찾을 수 없습니다."; // 상품이 없을 경우 처리
        }

        Cart cartItem = new Cart();
        cartItem.setProduct(product.get());
        cartItem.setQuantity(quantity);

        cartRepository.save(cartItem);
        return "상품이 장바구니에 추가되었습니다.";
    }

    // 장바구니에서 상품 삭제
    public String removeFromCart(Long cartId) {
        Optional<Cart> cartItem = cartRepository.findById(cartId);
        if (cartItem.isPresent()) {
            cartRepository.delete(cartItem.get());
            return "장바구니에서 상품이 삭제되었습니다.";
        } else {
            return "해당 상품이 장바구니에 없습니다.";
        }
    }
}
