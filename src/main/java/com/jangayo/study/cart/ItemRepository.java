package com.jangayo.study.cart;

import com.jangayo.study.cart.entity.Item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>{

    
    
}
