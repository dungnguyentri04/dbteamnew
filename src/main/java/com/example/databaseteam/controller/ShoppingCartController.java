package com.example.databaseteam.controller;

import com.example.databaseteam.model.CartItem;
import com.example.databaseteam.model.Product;
import com.example.databaseteam.model.ShoppingCart;
import com.example.databaseteam.model.UserDtls;
import com.example.databaseteam.service.CartItemsService;
import com.example.databaseteam.service.ProductService;
import com.example.databaseteam.service.ShoppingCartService;
import com.example.databaseteam.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private CartItemsService cartItemsService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/cart")
    public String cart(Model m, Principal principal, HttpSession session){
        if (principal==null){
            return "redirect://login";
        }
        UserDtls user = userService.getUserDtlsByEmail(principal.getName());

        ShoppingCart cart = user.getShoppingCart();
        System.out.println(cart);
        m.addAttribute("username", user.getName());
        if (cart!=null) {
            Set<CartItem> cartItems = cart.getCartItems();
            m.addAttribute("cartItems", cartItems);
            m.addAttribute("user", user);
            m.addAttribute("totalPrice",cart.getTotalPrice());
            m.addAttribute("totalItem",cart.getTotalItems());
        }
        else {
            // Xử lý trường hợp cart là null, ví dụ:
            m.addAttribute("cartItems", new HashSet<>());
            m.addAttribute("user", user);
            m.addAttribute("totalPrice",0);
            m.addAttribute("totalItem",0);

        }
        return "cart";
    }

    @GetMapping("/add-to-cart")
    public String addToCart(@RequestParam("id") int id,
                            HttpServletRequest request,
                            Model model,
                            Principal principal,
                            HttpSession session){
        if (principal==null){
            return "login";
        }
        UserDtls user = userService.getUserDtlsByEmail(principal.getName());
        Product product = productService.getProductById(id);
        ShoppingCart shoppingCart = shoppingCartService.addToCart(product,user);
        return "redirect:/product";
    }

    @PostMapping(value = "/update-cart")
    public String updateCart(@RequestBody UpdateCartRequest request,Model model,
                             Principal principal,
                             HttpSession session){
        String action = request.getAction();
        if (principal == null){
            return "redirect://login";
        }
        UserDtls user  = userService.getUserDtlsByEmail(principal.getName());
        Product product = productService.getProductById(request.getId());
        if (action.equals("delete")){
            ShoppingCart shoppingCart = shoppingCartService.deleteItemFromCart(product,user);
        } else if (action.equals("update")) {
            ShoppingCart shoppingCart = shoppingCartService.updateItemFromCart(product,user);
        }
        else {
            ShoppingCart shoppingCart = shoppingCartService.removeItemFromCart(product,user);
        }
        return "cart";
    }

    @Data
    public static class UpdateCartRequest {
        private int id;
        private String action;
    }

}
