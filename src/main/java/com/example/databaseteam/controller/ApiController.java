package com.example.databaseteam.controller;

import com.example.databaseteam.model.Product;
import com.example.databaseteam.model.UserDtls;
import com.example.databaseteam.service.ProductService;
import com.example.databaseteam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/api/product/{id}")
    public Product getProduct(@PathVariable int id){
        Product product = productService.getProductById(id);
        return product;
    }

    @GetMapping("/api/user/{id}")
    public UserDtls getUser(@PathVariable int id){
        UserDtls user = userService.getUserDtlsById(id);
        return user;
    }
}
