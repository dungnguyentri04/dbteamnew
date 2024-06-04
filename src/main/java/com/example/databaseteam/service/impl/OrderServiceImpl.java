package com.example.databaseteam.service.impl;

import com.example.databaseteam.model.*;
import com.example.databaseteam.repository.OrderDetailRepository;
import com.example.databaseteam.repository.OrderRepository;
import com.example.databaseteam.service.CartItemsService;
import com.example.databaseteam.service.OrderService;
import com.example.databaseteam.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public Order save(ShoppingCart cart, ShippingAddress shippingAddress) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setPaymentMethod("money");
        order.setAccept(false);
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderDate(new Date());
        order.setQuantity(cart.getTotalItems());
        order.setShippingAddress(shippingAddress);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(50);
            orderDetail.setPrice(50.0);
            orderDetailRepository.save(orderDetail);//
            orderDetailList.add(orderDetail);
        }
        order.setOrderDetailList(orderDetailList);
        shoppingCartService.deleteCartById(cart.getId());
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }
}
