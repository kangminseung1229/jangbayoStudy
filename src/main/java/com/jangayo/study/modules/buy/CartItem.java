package com.jangayo.study.modules.buy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.security.auth.message.callback.PrivateKeyCallback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long cnt;

    // 1과 N, N과 1 관계에서 양방향 관계는 항상 N 이 연관관계의 주인이다.
    @ManyToOne
    private Cart cart;



    //기존 
    // @OneToOne //상품에 대한 1:1 맵핑
    // @JoinColumn(name = "item_id")
    // private Item item;


    // @ManyToOne
    // @JoinColumn(name = "cart_id")
    // private Cart cart;
}
