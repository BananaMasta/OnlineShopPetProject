package org.onlineshop.controllers;

import org.onlineshop.models.Role;
import org.onlineshop.models.User;
import org.onlineshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@PreAuthorize("hasAuthority('Admin')")
public class AdminController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/adminpanel")
    public String adminPanel(Model model) {
        model.addAttribute("userslist", userRepository.findAll());
        return "adminpanel";
    }

    @GetMapping("/userupdate/{id}")
    public String userUpdate(@PathVariable String id, Model model) {
        User user = userRepository.findById(Long.valueOf(id)).get();
        model.addAttribute("id", id);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userlogin", user.getLogin());
        model.addAttribute("userpassword", user.getPassword());
        return "userupdate";
    }

    @PostMapping("/adminpanel/userban/{id}")
    public String banUser(@PathVariable String id) {
        User user = userRepository.findById(Long.valueOf(id)).get();
        if (user.isActive()) {
            user.setActive(false);
            userRepository.save(user);
        } else {
            user.setActive(true);
            userRepository.save(user);
        }
        return "redirect:/adminpanel";
    }

    @PostMapping("/userupdate")
    public String userUpdate(@RequestParam(name = "id") String id, @RequestParam(name = "name") String userName, @RequestParam(name = "login") String userLogin,
                             @RequestParam(name = "password") String userPassword,
                             @RequestParam(name = "user", required = false) String radioUser, @RequestParam(name = "admin", required = false) String radioAdmin) {
        User user = userRepository.findById(Long.valueOf(id)).get();
        List<Role> roleList = new ArrayList<>();
        user.setName(userName);
        user.setLogin(userLogin);
        user.setPassword(userPassword);
        if (user.getUserRole().contains(Role.Admin) && !radioUser.isEmpty()) {
            roleList.add(Role.User);
        } else if(user.getUserRole().contains(Role.User) && !radioAdmin.isEmpty()) {
            roleList.add(Role.Admin);
        }
        user.setUserRole(roleList);
        userRepository.save(user);
        return "redirect:/adminpanel";
    }
}
