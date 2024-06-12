package com.example.databaseteam.controller;

import com.example.databaseteam.model.Order;
import com.example.databaseteam.model.ShippingAddress;
import com.example.databaseteam.model.ShoppingCart;
import com.example.databaseteam.model.UserDtls;
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
        m.addAttribute("cart",cart);
        m.addAttribute("username", user.getName());
        m.addAttribute("totalItem",cart.getTotalItems());
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
//            System.out.println("123");
//            System.out.println(shippingAddress.getName());
            UserDtls user = userService.getUserDtlsByEmail(principal.getName());
            ShoppingCart cart = user.getShoppingCart();
            System.out.println(cart.getId());
            Order order = orderService.save(cart,shippingAddress);
//            ShippingAddress shippingInfo = shippingAddressService.save(shippingAddress,order);
            return "redirect:/index";
        }
    }

}
