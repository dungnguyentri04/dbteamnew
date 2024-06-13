package com.example.databaseteam.service;

import com.example.databaseteam.model.Order;
import com.example.databaseteam.model.ShippingAddress;
import com.example.databaseteam.model.ShoppingCart;

import java.util.List;

public interface ShippingAddressService {
//    public ShippingAddress save(ShippingAddress shippingAddress, Order order);
    public List<ShippingAddress> getAllShippingAddress();
}
