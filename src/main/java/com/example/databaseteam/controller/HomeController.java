package com.example.databaseteam.controller;

import com.example.databaseteam.model.ShoppingCart;
import com.example.databaseteam.model.UserDtls;
import com.example.databaseteam.service.ProductService;
import com.example.databaseteam.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String index(Model m, Principal principal){
        if (principal!=null){
            UserDtls user = userService.getUserDtlsByEmail(principal.getName());
            ShoppingCart cart = user.getShoppingCart();
            m.addAttribute("username",user.getName());
            if (cart!=null) {
                m.addAttribute("totalItem", cart.getTotalItems());
            }
            else  m.addAttribute("totalItem",0);
        }
        m.addAttribute("products",productService.getAllProducts());
        return "index";
    }

    @GetMapping("/product")
    public String product(Model m,Principal principal){
        if (principal!=null){
            UserDtls user = userService.getUserDtlsByEmail(principal.getName());
            ShoppingCart cart = user.getShoppingCart();
            m.addAttribute("username",user.getName());
            if (cart!=null) {
                m.addAttribute("totalItem", cart.getTotalItems());
            }
            else m.addAttribute("totalItem",0);
        }
        m.addAttribute("products",productService.getAllProducts());
        return "product";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/about")
    public String about(Principal principal,Model m){
        if (principal!=null){
            UserDtls user = userService.getUserDtlsByEmail(principal.getName());
            ShoppingCart cart = user.getShoppingCart();
            m.addAttribute("username",user.getName());
            if (cart!=null) {
                m.addAttribute("totalItem", cart.getTotalItems());
            }
            else m.addAttribute("totalItem",0);
        }
        return "about";
    }

    @GetMapping("/blog")
    public String blog(Principal principal,Model m){
        if (principal!=null){
            UserDtls user = userService.getUserDtlsByEmail(principal.getName());
            ShoppingCart cart = user.getShoppingCart();
            m.addAttribute("username",user.getName());
            if (cart!=null) {
                m.addAttribute("totalItem", cart.getTotalItems());
            }
            else m.addAttribute("totalItem",0);
        }
        return "blog";
    }

    @GetMapping("/gallery")
    public String gallery(Principal principal,Model m){
        if (principal!=null){
            UserDtls user = userService.getUserDtlsByEmail(principal.getName());
            ShoppingCart cart = user.getShoppingCart();
            m.addAttribute("username",user.getName());
            if (cart!=null) {
                m.addAttribute("totalItem", cart.getTotalItems());
            }
            else m.addAttribute("totalItem",0);
        }
        return "gallery";
    }


    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserDtls user, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
        user.setProfileImage(imageName);
        UserDtls saveUser = userService.saveUser(user);
        if (!ObjectUtils.isEmpty(saveUser))
        {
            if (!file.isEmpty()) {
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "profile_img" + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            session.setAttribute("successMsg","Saved successfully");
        } else {
            session.setAttribute("errorMsg","Something wrong on server");
        }
        return "login";
    }


}
