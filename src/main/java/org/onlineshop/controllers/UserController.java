package org.onlineshop.controllers;

import org.onlineshop.models.*;
import org.onlineshop.repository.*;
import org.onlineshop.service.UserService;
import org.onlineshop.util.DtoProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserBasketRepository userBasketRepository;

    @Autowired
    ProductInBasketRepository productInBasketRepository;

    @Autowired
    PaymentHistoryRepository paymentHistoryRepository;


    @GetMapping("/login")
    public String userLoginPage() {
        return "userlogin";
    }

    @GetMapping("mainpage")
    public String getMainPage(Model model) {
        List<Product> product = productRepository.findAll();
        model.addAttribute("productlist", product);
        return "mainpage";
    }

    @GetMapping("/registration")
    public String accountRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "userRegistration";
    }

    @GetMapping("/userbasket")
    public String userBasket(@AuthenticationPrincipal User user, Model model) {
        UserBasket userBasket = userBasketRepository.findByUser(user);
        List<DtoProduct> dtoProductList = new ArrayList<>();
        if (userBasket.getProduct() == null) {
            model.addAttribute("productBasketList", dtoProductList);
            return "userBasket";
        }
        List<ProductInBasket> productList = userBasket.getProduct();
        double sum = 0.0;
        for (int i = 0; i < productList.size(); i++) {
            DtoProduct productBasketDto = new DtoProduct();
            productBasketDto.setFileName(productList.get(i).getProduct().getFileName());
            productBasketDto.setProductName(productList.get(i).getProduct().getName());
            productBasketDto.setDescription(String.valueOf(productList.get(i).getProduct().getProductCategory()));
            productBasketDto.setProductQuantity((int) userBasket.getProduct().get(i).getQuantity());
            productBasketDto.setProductPrice(productList.get(i).getProduct().getPrice());
            productBasketDto.setSum(productBasketDto.getProductPrice().multiply(BigDecimal.valueOf(productBasketDto.getProductQuantity())));
            dtoProductList.add(productBasketDto);
            sum = sum + productBasketDto.getSum().doubleValue();
        }
        model.addAttribute("productBasketList", dtoProductList);
        model.addAttribute("sumForAllProd", sum);
        return "userBasket";
    }

    @GetMapping("/userbasket/{id}")
    public String addIntoBasket(@AuthenticationPrincipal User user, @PathVariable String id) {
        UserBasket userBasket = userBasketRepository.findByUser(user);
        Product product = productRepository.findById(Long.valueOf(id)).get();
        if (userBasket.getProduct() == null) {
            return "redirect:/userbasket";
        }
        List<ProductInBasket> productInBaskets = userBasket.getProduct();
        long count = userBasket.getProduct().stream().map(x -> x.getProduct()).filter(x -> x.equals(product)).count();
        if (count > 0) {
            ProductInBasket productInBasket = userBasket.getProduct().stream().filter(x -> x.getProduct().equals(product)).findFirst().get();
            int position = userBasket.getProduct().indexOf(productInBasket);
            productInBasket.setQuantity(productInBasket.getQuantity() + 1);
            productInBasketRepository.save(productInBasket);
            List<ProductInBasket> productInBaskets1 = userBasket.getProduct();
            productInBaskets1.set(position, productInBasket);
            userBasket.setProduct(productInBaskets1);
            userBasketRepository.save(userBasket);
            return "redirect:/userbasket";
        }
        ProductInBasket productInBasket = new ProductInBasket();
        productInBasket.setProduct(product);
        productInBasket.setQuantity(1);
        productInBasketRepository.save(productInBasket);
        productInBaskets.add(productInBasket);
        userBasket.setProduct(productInBaskets);
        userBasketRepository.save(userBasket);
        return "redirect:/userbasket";
    }

    @GetMapping("/paymenthistory")
    public String paymentHistory(@AuthenticationPrincipal User user, Model model) {
        List<PaymentHistory> paymentHistory = paymentHistoryRepository.findByUser(user);
        model.addAttribute("paymentHistoryList", paymentHistory);
        BigDecimal summary = new BigDecimal(0);
        for (int i = 0; i < paymentHistory.size(); i++) {
            for (int j = 0; j < paymentHistory.get(i).getProduct().size(); j++) {
                BigDecimal productPrice = paymentHistory.get(i).getProduct().get(j).getProduct().getPrice();
                int prodQuantity = (int) paymentHistory.get(i).getProduct().get(j).getQuantity();
                summary = summary.add(productPrice.multiply(BigDecimal.valueOf(prodQuantity)));
            }
        }
        System.out.println(summary);
        model.addAttribute("sum", summary);
        return "paymentHistory";
    }

    @PostMapping("/payforprod")
    public String payForProduct(@AuthenticationPrincipal User user) {
        UserBasket userBasket = userBasketRepository.findByUser(user);
        if (userBasket == null) {
            System.out.println("Корзина пустая");
        } else {
            userBasket.setUser(null);
            userBasketRepository.save(userBasket);
            PaymentHistory paymentHistory = new PaymentHistory();
            List<ProductInBasket> newProductList = new ArrayList<>();
            for (int i = 0; i < userBasket.getProduct().size(); i++) {
                newProductList.add(userBasket.getProduct().get(i));
            }
            paymentHistory.setProduct(newProductList);
            paymentHistory.setUser(user);
            paymentHistoryRepository.save(paymentHistory);
            UserBasket newUserBasket = new UserBasket();
            newUserBasket.setUser(user);
            userBasketRepository.save(newUserBasket);
            return "redirect:/userBasket";
        }
        return "userBasket";
    }

    @PostMapping("/registration")
    public String userRegistrationPage(User user) {
        if (!userService.registerUser(user)) {
            return "redirect:/userRegistration";
        } else {
            UserBasket userBasket = new UserBasket();
            userBasket.setUser(user);
            userBasketRepository.save(userBasket);
            return "userlogin";
        }
    }

    @PostMapping("/userbasket/delete")
    public String deleteProductFromBasket(@AuthenticationPrincipal User user) {
        UserBasket userBasket = userBasketRepository.findByUser(user);
        if (userBasket.getProduct().size() == 0) {
            System.out.println("Корзина пуста, нечего удалять");
        } else {
            List<ProductInBasket> newProductInBasket = new ArrayList<>(userBasket.getProduct());
            userBasket.getProduct().clear();
            userBasketRepository.save(userBasket);
            for (int i = 0; i < newProductInBasket.size(); i++) {
                productInBasketRepository.delete(newProductInBasket.get(i));
            }
            return "redirect:/mainpage";
        }
        return "redirect:/mainpage";
    }
}
