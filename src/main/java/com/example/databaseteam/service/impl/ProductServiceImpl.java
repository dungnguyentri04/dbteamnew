package com.example.databaseteam.service.impl;

import com.example.databaseteam.model.Product;
import com.example.databaseteam.repository.ProductRepository;
import com.example.databaseteam.service.CartItemsService;
import com.example.databaseteam.service.OrderDetailService;
import com.example.databaseteam.service.OrderService;
import com.example.databaseteam.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CartItemsService cartItemsService;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);// return product after save product into database
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if (!ObjectUtils.isEmpty(product)){
            orderDetailService.deleteOrderDetailByProductId(id);
            cartItemsService.deleteCartItemByProductId(id);
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product updateProduct(Product product, MultipartFile image) throws IOException {
        Product changeProduct = getProductById(product.getId());
        String imageName = image.isEmpty() ? changeProduct.getImage() : image.getOriginalFilename();
        changeProduct.setPrice(product.getPrice());
        changeProduct.setDescription(product.getDescription());
        changeProduct.setTitle(product.getTitle());
        changeProduct.setStock(product.getStock());
        changeProduct.setDiscount(product.getDiscount());
        changeProduct.setImage(imageName);
        Product updateProduct = productRepository.save(changeProduct);
        if (!ObjectUtils.isEmpty(updateProduct)){
            if (!image.isEmpty()){
                try {
                    File saveFile = new ClassPathResource("static/img").getFile();
                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator + image.getOriginalFilename());
                    Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return product;
        }
        return null;
    }
}
