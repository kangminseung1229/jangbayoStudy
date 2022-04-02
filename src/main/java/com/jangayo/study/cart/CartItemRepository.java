package com.jangayo.study.cart;

import com.jangayo.study.cart.entity.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository  extends JpaRepository<CartItem, Long>{
    
}
