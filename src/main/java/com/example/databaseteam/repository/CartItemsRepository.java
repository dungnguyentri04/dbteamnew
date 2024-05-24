package com.example.databaseteam.repository;

import com.example.databaseteam.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItem,Integer> {
}
