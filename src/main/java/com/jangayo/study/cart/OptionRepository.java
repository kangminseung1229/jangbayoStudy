package com.jangayo.study.cart;

import java.util.List;

import com.jangayo.study.cart.entity.ItemOption;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<ItemOption, Long> {

    List<ItemOption> findByItemId(Long id);


    
}
