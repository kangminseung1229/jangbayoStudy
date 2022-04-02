package com.jangayo.study.cart;

import java.util.Optional;

import com.jangayo.study.account.Account;
import com.jangayo.study.cart.entity.CartItem;
import com.jangayo.study.cart.entity.ItemOption;
import com.jangayo.study.cart.form.OptionForm;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ItemOptionRepository itemOptionRepository;
    
    //장바구니 담기
    public void initCartItem(Account account, OptionForm optionForm) {

        // 위 파라미터는 준영속 상태

        CartItem newCartItem = new CartItem();
        
        Optional<ItemOption> newItemOption = itemOptionRepository.findById(optionForm.getOptionId());

        newItemOption.ifPresent(form -> {
            newCartItem.setOption(form);
            newCartItem.setCnt(optionForm.getCnt());
        });
        cartItemRepository.save(newCartItem);
    }

}
