package org.onlineshop.controllers;

import org.onlineshop.util.ProductBasketDto;
import org.onlineshop.models.ProductInBasket;
import org.onlineshop.models.User;
import org.onlineshop.models.UserBasket;
import org.onlineshop.repository.ProductInBasketRepository;
import org.onlineshop.repository.ProductRepository;
import org.onlineshop.repository.UserBasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class BasketController {
    @Autowired
    UserBasketRepository userBasketRepository;

    @Autowired
    ProductInBasketRepository productInBasketRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/basket")
    public String basket(@AuthenticationPrincipal User user, Model model) {
        ProductBasketDto productBasketDto = new ProductBasketDto();
        UserBasket userBasket = userBasketRepository.findByUser(user);
        List<ProductInBasket> productInBasketList = userBasket.getProduct();
        for (int i = 0; i < productInBasketList.size(); i++) {
            productBasketDto.setTitle(productInBasketList.get(i).getProduct().getName());
            productBasketDto.setDescription(String.valueOf(productInBasketList.get(i).getProduct().getProductCategory()));
            productBasketDto.setQuantity((int) userBasket.getProduct().get(i).getQuantity());
            productBasketDto.setPrice(productInBasketList.get(i).getProduct().getPrice());
            productBasketDto.setSum(productBasketDto.getSum().multiply(BigDecimal.valueOf(productBasketDto.getQuantity())));
        }
        model.addAttribute("basketList", productInBasketList);
        return "basket";
    }

}
