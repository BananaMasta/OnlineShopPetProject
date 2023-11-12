package org.onlineshop.controllers;

import org.jetbrains.annotations.NotNull;
import org.onlineshop.models.*;

import org.onlineshop.repository.*;
import org.onlineshop.service.FileService;
import org.onlineshop.service.ProductService;
import org.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    ProductRatingRepository productRatingRepository;
    @Value("${spring.upload.path}")
    private String userImagePath;
    @Autowired
    PaymentHistoryRepository paymentHistoryRepository;
    @Autowired
    UserService userService;
    @Autowired
    CommentaryRepository commentaryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    ProductService productService;


//    @GetMapping("/prod")
//    public String prod(Model model) {
//        List<Product> product = productRepository.findAll();
//        model.addAttribute("products", product);
//        return "prod";
//    }

    @GetMapping("/newproduct")
    public String getProduct(Model model) {
        List<String> productCategoriesList = productCategoryRepository.findAll().stream().map(ProductCategory::getCategoryName).collect(Collectors.toList());
        List<String> companyList = companyRepository.findAll().stream().map(Company::getName).collect(Collectors.toList());
        model.addAttribute("categorylist", productCategoriesList);
        model.addAttribute("companylist", companyList);
        return "productCreate";
    }

    @GetMapping("/productpage/{id}")
    public String productPage(@PathVariable String id, Model model) {
        Product product = productRepository.findById(Long.valueOf(id)).get();
        List<UserCommentary> userCommentaryList = commentaryRepository.findByProduct(product);
        List<Product> list = new ArrayList<>();
        model.addAttribute("productlist", list);
        model.addAttribute("productid", id);
        model.addAttribute("usercomments", userCommentaryList);
        model.addAttribute("productrating", productService.productRating(Long.parseLong(id)));
        return "productPage";
    }

    @GetMapping("/newcategory")
    public String getCategory() {
        return "categoryCreate";
    }

    @GetMapping("/newcompany")
    public String getCompany(Model model) {
        List<String> productList = productRepository.findAll().stream().map(Product::getName).collect(Collectors.toList());
        model.addAttribute("productlist", productList);
        return "companyCreate";
    }


    @PostMapping("/newproduct")
    public String postNewProduct(@RequestParam("product") String productName, @RequestParam("productprice") BigDecimal productPrice,
                                 @RequestParam("shopcategory") String category, @RequestParam("shopcompany") String company,
                                 @RequestParam("file") MultipartFile file, ModelMap modelMap, Model model) throws IOException {
        System.out.println(file.getOriginalFilename());
        if (!productRepository.existsByName(productName)) {
            ProductCategory productCategory = productCategoryRepository.findByCategoryName(category);
            Company companyName = companyRepository.findByName(company);
            Product product = new Product(productName, productPrice, productCategory, companyName);
            String newFileName = FileService.rndFileName() + "." + file.getOriginalFilename();
            String fullName = userImagePath + "/" + newFileName;
            product.setFileName(newFileName);
            file.transferTo(new File(fullName));
            productRepository.save(product);
        }
        return "productCreate";
    }

    @PostMapping("/newcategory")
    public String postCategoryCreate(@RequestParam("category") String categoryName) {
        if (!productCategoryRepository.existsByCategoryName(categoryName)) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setCategoryName(categoryName);
            productCategoryRepository.save(productCategory);
        }
        return "categoryCreate";
    }

    @PostMapping("/newcompany")
    public String postCompany(@RequestParam(name = "companyname") String name) {
        if (!companyRepository.existsByName(name)) {
            Company company = new Company();
            company.setName(name);
            companyRepository.save(company);
        }
        return "companyCreate";
    }

    @PostMapping("/productpage/{id}")
    public String productPostPage(@RequestParam(name = "id") String id, @RequestParam(name = "userid") String userId, Model model) {
        model.addAttribute("userrating", productService.userRating(Long.parseLong(id), Long.parseLong(userId)));
        return "productPage";
    }


    @PostMapping("/productpage/{id}/productscore")
    public String productRating(@RequestParam(name = "id") String id, @AuthenticationPrincipal User user, @RequestParam(name = "rating") String prodRating) {
        ProductRating productRating = productRatingRepository.findByUser(user);
        Product product = productRepository.findById(Long.valueOf(id)).get();
        if (productRating != null && productRating.getUser().equals(user)) {
            System.out.println("Вы уже оценивали товар");
        } else {
            ProductRating newProductRating = new ProductRating();
            if (prodRating.equals("1")) {
                newProductRating.setUser(user);
                newProductRating.setProduct(product);
                newProductRating.setRating((byte) 1);
                product.setProductRating(newProductRating);
                productRatingRepository.save(newProductRating);
                productRepository.save(product);
            } else if (prodRating.equals("2")) {
                newProductRating.setUser(user);
                newProductRating.setProduct(product);
                newProductRating.setRating((byte) 2);
                product.setProductRating(newProductRating);
                productRatingRepository.save(newProductRating);
                productRepository.save(product);
            } else if (prodRating.equals("3")) {
                newProductRating.setUser(user);
                newProductRating.setProduct(product);
                newProductRating.setRating((byte) 3);
                product.setProductRating(newProductRating);
                productRatingRepository.save(newProductRating);
                productRepository.save(product);
            } else if (prodRating.equals("4")) {
                newProductRating.setUser(user);
                newProductRating.setProduct(product);
                newProductRating.setRating((byte) 4);
                product.setProductRating(newProductRating);
                productRatingRepository.save(newProductRating);
                productRepository.save(product);
            } else if (prodRating.equals("5")) {
                newProductRating.setUser(user);
                newProductRating.setProduct(product);
                newProductRating.setRating((byte) 5);
                product.setProductRating(newProductRating);
                productRatingRepository.save(newProductRating);
                productRepository.save(product);
            }
        }
        return "productPage";
    }

    @PostMapping("/productpage/{id}/commentary")
    public String addUserComment(@AuthenticationPrincipal User user, @RequestParam(name = "id") String id,
                                 @RequestParam(name = "usercomment") String usercommentary) {
        Product product = productRepository.findById(Long.valueOf(id)).get();
        if (!userService.userCommentaryCheck(user, Long.parseLong(id))) {
            UserCommentary userCommentary = new UserCommentary();
            userCommentary.setUser(user);
            userCommentary.setUserComment(usercommentary);
            userCommentary.setProduct(product);
            commentaryRepository.save(userCommentary);
            return "redirect:/productpage/" + id;
        } else {
            return "redirect:/mainpage";
        }
    }

    @GetMapping("/usercommentary")
    public String findUserComments() {
        return "usercommentary";
    }

    @PostMapping("/usercommentary")
    public String userCommentarySearchResult(@AuthenticationPrincipal User user, @RequestParam(name = "username") String userName, Model model) {
        User findUser = userRepository.findByLogin(userName);
        List<UserCommentary> userCommentaryList = commentaryRepository.findByUser(findUser);
        model.addAttribute("usercom", userCommentaryList);
        return "usercommentary";
    }
}