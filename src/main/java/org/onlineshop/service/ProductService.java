package org.onlineshop.service;

import org.onlineshop.models.*;
import org.onlineshop.repository.CommentaryRepository;
import org.onlineshop.repository.ProductRatingRepository;
import org.onlineshop.repository.ProductRepository;
import org.onlineshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductRatingRepository productRatingRepository;
    @Autowired
    private CommentaryRepository commentaryRepository;
    @Autowired
    private UserRepository userRepository;

    public double productRating(long id) {
        double sum = 0;
        Product product = productRepository.findById(id).get();
        List<DtoRating> dtoRatingList = new ArrayList<>();
        List<ProductRating> productRatingList = productRatingRepository.findByProduct(product);
        DtoRating dtoRating = new DtoRating();
        dtoRating.setProduct(product);
        for (int i = 0; i < productRatingList.size(); i++) {
            sum = (sum + productRatingList.get(i).getRating());

        }
        double finalScore = sum / productRatingList.size();
        dtoRating.setFinalScore(finalScore);
        return finalScore;
    }

    public byte userRating(long id, long userid) {
        byte rating = 0;
        Product product = productRepository.findById(id).get();
        User user = userRepository.findById(userid).get();
        ProductRating userRating = productRatingRepository.findByUser(user);
        List<ProductRating> productRatingList = productRatingRepository.findByProduct(product);
        for (int i = 0; i < productRatingList.size(); i++) {
            if (userRating.getUser().equals(productRatingList.get(i).getUser())) {
                rating = productRatingList.get(i).getRating();
            }
        }
        return rating;
    }
}
