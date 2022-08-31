package com.av.dao;

import com.av.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, Long> {

    List<Book> findAll();


    List<Book> findBooksByTitle(String title);


}
