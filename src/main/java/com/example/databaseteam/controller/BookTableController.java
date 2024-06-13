package com.example.databaseteam.controller;

import com.example.databaseteam.model.BookTable;
import com.example.databaseteam.model.UserDtls;
import com.example.databaseteam.service.BookTableService;
import com.example.databaseteam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class BookTableController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookTableService bookTableService;

    @PostMapping("/add-booktable")
    public String createBookTable(Principal principal, @ModelAttribute BookTable bookTable){
        if (principal==null){
            return "redirect:/login";
        }
        else {
            UserDtls user = userService.getUserDtlsByEmail(principal.getName());
            BookTable saveBookTable = bookTableService.save(user,bookTable);
            return "redirect:/index";

        }
    }
}
