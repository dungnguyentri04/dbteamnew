package com.example.databaseteam.controller;

import com.example.databaseteam.model.*;
import com.example.databaseteam.repository.UserRepository;
import com.example.databaseteam.service.OrderService;
import com.example.databaseteam.service.ShippingAddressService;
import com.example.databaseteam.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@Controller
public class OrderController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    ShippingAddressService shippingAddressService;

    @GetMapping("/checkout")
    public String checkout(Principal principal, Model m){
        if (principal==null){
            return "redirect://login";
        }
        UserDtls user = userService.getUserDtlsByEmail(principal.getName());
        ShoppingCart cart = user.getShoppingCart();
        Set<CartItem> cartItems = cart.getCartItems();
        m.addAttribute("cartItems",cartItems);
        m.addAttribute("username", user.getName());
        m.addAttribute("totalItem",cart.getTotalItems());
        m.addAttribute("totalPrice",cart.getTotalPrice());
        return "checkout";
    }

    @PostMapping(value = "/add-order")
    public String createOrder(Principal principal,
                              Model model,
                              HttpSession session,
                              @ModelAttribute ShippingAddress shippingAddress) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            UserDtls user = userService.getUserDtlsByEmail(principal.getName());
            ShoppingCart cart = user.getShoppingCart();
            System.out.println(cart.getId());
            Order order = orderService.save(cart,shippingAddress);
            return "redirect:/index";
        }
    }

}
