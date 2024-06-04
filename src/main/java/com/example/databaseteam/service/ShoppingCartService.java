package com.example.databaseteam.service;

import com.example.databaseteam.model.Product;
import com.example.databaseteam.model.ShoppingCart;
import com.example.databaseteam.model.UserDtls;

import java.util.List;

public interface ShoppingCartService {
    public ShoppingCart addToCart(Product product, UserDtls user);
    public ShoppingCart deleteItemFromCart(Product product, UserDtls user);
    public ShoppingCart updateItemFromCart(Product product, UserDtls user);
    public ShoppingCart removeItemFromCart(Product product, UserDtls user);
    public List<ShoppingCart> getAllShoppingCart();
    public void deleteCartById(int id);
}
