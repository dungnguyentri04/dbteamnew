package com.example.databaseteam.repository;

import com.example.databaseteam.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {

}
