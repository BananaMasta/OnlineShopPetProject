package org.onlineshop.service;

import org.onlineshop.models.*;
import org.onlineshop.repository.CommentaryRepository;
import org.onlineshop.repository.PaymentHistoryRepository;
import org.onlineshop.repository.ProductRepository;
import org.onlineshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PaymentHistoryRepository paymentHistoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private CommentaryRepository commentaryRepository;

    public boolean registerUser(User user) {
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(Role.Admin);
        user.setUserRole(userRoles);
        user.setActive(true);
        if (userRepository.existsByLogin(user.getLogin())) {
            return false;
        } else {
            userRepository.save(user);
            return true;
        }
    }

    public boolean userPaymentHistory(User user, long id) {
        List<PaymentHistory> paymentHistory = paymentHistoryRepository.findByUser(user);
        Product product = productRepository.findById(id).get();
        for (int i = 0; i < paymentHistory.size(); i++) {
            if (paymentHistory.get(i).getProduct().contains(product)) {
                return true;
            }
        }
        return false;
    }

    public boolean userCommentaryCheck(User user, long id) {
        Product product = productRepository.findById(id).get();
        List<UserCommentary> userCommentaryList = commentaryRepository.findByProduct(product);
        for (int i = 0; i < userCommentaryList.size(); i++) {
            if (userCommentaryList.get(i).getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }
}
