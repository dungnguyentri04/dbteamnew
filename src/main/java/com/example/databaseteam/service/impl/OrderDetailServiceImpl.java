package com.example.databaseteam.service.impl;

import com.example.databaseteam.model.OrderDetail;
import com.example.databaseteam.repository.OrderDetailRepository;
import com.example.databaseteam.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public void deleteOrderDetailByProductId(int id) {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        for (OrderDetail orderDetail:orderDetails){
            if (orderDetail.getProduct().getId()==id){
                orderDetailRepository.delete(orderDetail);
            }
        }
    }
}
