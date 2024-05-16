package com.example.databaseteam.controller;


import com.example.databaseteam.model.Product;
import com.example.databaseteam.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @GetMapping("/index")
    public String index(){
        return "admin/index";
    }

    @GetMapping("/products")
    public String viewProduct(Model m){
        m.addAttribute("products",productService.getAllProducts());
        return "admin/products";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Product product, HttpSession session, @RequestParam("file") MultipartFile image) throws IOException {
        String imageName = image.isEmpty()?"default.jpg":image.getOriginalFilename();
        product.setImage(imageName);
        product.setDiscount(product.getDiscount());
        product.setDiscountPrice(product.getDiscountPrice());
        Product saveProduct = productService.saveProduct(product);//return new product
        if (!ObjectUtils.isEmpty(saveProduct)){
            File saveFile = new ClassPathResource("static/img").getFile();
            Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+"product_img"+File.separator+image.getOriginalFilename());
            Files.copy(image.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            session.setAttribute("successMsg","product saved success");
        }
        else {
            session.setAttribute("errorMsg","something wrong on server");
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable int id,HttpSession session){
        Boolean deleteProduct = productService.deleteProduct(id);
        System.out.println("hello"+id);
        if (deleteProduct){
            session.setAttribute("successMsg","product delete success");
        }
        else {
            session.setAttribute("errorMsg","something wrong on server");
        }
        return "redirect:/admin/products";
    }


    @PostMapping("/editProduct")
    public String editProduct(HttpSession session, @RequestParam("file") MultipartFile image,@ModelAttribute Product product) throws IOException {
        Product updateProduct = productService.updateProduct(product,image);
        if (!ObjectUtils.isEmpty(updateProduct)){
            session.setAttribute("successMsg","product saved success");
        }
        else {
            session.setAttribute("errorMsg","something wrong on server");
        }
        return "redirect:/admin/products";
    }






}
