package com.korea.it.shopping.cart.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    @Column(nullable = false)
    private int productId;

    @Column(nullable = false, length = 50)
    private String productName;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private int price;

}
