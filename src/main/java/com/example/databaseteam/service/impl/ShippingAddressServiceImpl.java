package com.example.databaseteam.service.impl;

import com.example.databaseteam.model.Order;
import com.example.databaseteam.model.ShippingAddress;
import com.example.databaseteam.repository.ShippingAddressRepository;
import com.example.databaseteam.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

//    @Override
//    public ShippingAddress save(ShippingAddress shippingAddress, Order order) {
//        shippingAddress.setOrder(order);
//        System.out.println("hello");
//        return shippingAddressRepository.save(shippingAddress);
//    }

    @Override
    public List<ShippingAddress> getAllShippingAddress() {
        return shippingAddressRepository.findAll();
    }
}
