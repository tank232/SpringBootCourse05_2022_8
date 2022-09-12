package com.av.dao;

import com.av.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findAll();


    List<Book> findBooksByTitle(String title);

    List<Book> findBooksByAuthors(String author);


}
