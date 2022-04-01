package com.jangayo.study.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.jangayo.study.account.Account;
import com.jangayo.study.account.CurrentUser;
import com.jangayo.study.cart.entity.Item;
import com.jangayo.study.cart.entity.ItemOption;
import com.jangayo.study.cart.form.OptionForm;

import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final ItemRepository itemRepository;
    private final OptionRepository optionRepository;


    @GetMapping("/cart-list")
    public String cart(){
        return "cart/cart-list";
    }


    //상품 목록
    @GetMapping("/item-list")
    public String item(Model model){

        List<Item> itemList = itemRepository.findAll();

        model.addAttribute(itemList);

        return "cart/item-list";
    }

    //상품에 대한 옵션 상세
    @GetMapping("/item-option")
    public String option(@RequestParam Long id, Model model){

        List<ItemOption> options = optionRepository.findByItemId(id);

        Optional<Item> item = itemRepository.findById(id);

        item.ifPresent(target -> {
            model.addAttribute("item", target);
        });
         

        model.addAttribute("ItemOptions", options);
        
        return "cart/item-option";
    }

    //장바구니 담기 
    @PostMapping("/init-cartitem")
    @ResponseBody
    public void initCartitem(@RequestBody OptionForm optionForm){
        System.out.println(optionForm);
    }

    @GetMapping("/cart-test")
    @ResponseBody
    public HashMap<String, String>  test(@CurrentUser Account account){

        HashMap<String, String> map = new HashMap<String, String>();
        
        
        map.put("name", account.getNickname());
        map.put("auth", SecurityContextHolder.getContext().getAuthentication().toString());

        return map;

    }
    
}
