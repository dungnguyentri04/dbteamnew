package com.example.databaseteam.service.impl;

import com.example.databaseteam.model.Product;
import com.example.databaseteam.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Override
    public Product saveProduct(Product product) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        return null;
    }

    @Override
    public Product getProductById(Integer id) {
        return null;
    }

    @Override
    public Product updateProduct(Product product, MultipartFile file) throws IOException {
        return null;
    }
}
