package com.example.databaseteam.service;

import com.example.databaseteam.model.BookTable;
import com.example.databaseteam.model.ShippingAddress;
import com.example.databaseteam.model.UserDtls;

import java.util.List;

public interface BookTableService {
    public BookTable save(UserDtls user,BookTable bookTable);
    public List<BookTable> getAllBookTable();
    void deleteBookTableByUserId(int id);
}
