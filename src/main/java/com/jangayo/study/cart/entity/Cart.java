package com.jangayo.study.cart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Cart {
    
    @Id
    @GeneratedValue
    private Long id;

    private Long cnt;

}
