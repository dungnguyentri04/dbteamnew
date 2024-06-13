package com.example.databaseteam.service.impl;

import com.example.databaseteam.model.CartItem;
import com.example.databaseteam.repository.CartItemsRepository;
import com.example.databaseteam.service.CartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemsServiceImpl implements CartItemsService {
    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Override
    public void deleteCartItemByProductId(int id) {
        List<CartItem> cartItems = cartItemsRepository.findAll();
        for (CartItem cartItem: cartItems){
            if (cartItem.getProduct().getId()==id){
                cartItemsRepository.delete(cartItem);
            }
        }
    }
}
