package com.example.databaseteam.service.impl;

import com.example.databaseteam.model.BookTable;
import com.example.databaseteam.model.UserDtls;
import com.example.databaseteam.repository.BookTableRepository;
import com.example.databaseteam.service.BookTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookTableServiceImpl implements BookTableService {
    @Autowired
    private BookTableRepository bookTableRepository;

    @Override
    public BookTable save(UserDtls user, BookTable bookTable) {
        bookTable.setUser(user);
        return bookTableRepository.save(bookTable);
    }

    @Override
    public List<BookTable> getAllBookTable() {
        return bookTableRepository.findAll();
    }

    @Override
    public void deleteBookTableByUserId(int id) {
        List<BookTable> bookTables = bookTableRepository.findAll();
        for (BookTable bookTable:bookTables){
            if (bookTable.getUser().getId()==id){
                bookTableRepository.delete(bookTable);
            }
        }
    }
}
