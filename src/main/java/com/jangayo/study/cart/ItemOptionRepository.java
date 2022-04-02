package com.jangayo.study.cart;

import com.jangayo.study.cart.entity.ItemOption;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOptionRepository extends JpaRepository<ItemOption, Long>{
    
}
