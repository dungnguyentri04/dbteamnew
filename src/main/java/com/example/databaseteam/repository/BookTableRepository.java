package com.example.databaseteam.repository;

import com.example.databaseteam.model.BookTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTableRepository extends JpaRepository<BookTable,Integer> {
}
