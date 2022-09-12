package com.av.dao;

import com.av.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {

    List<Author> findAll();

    Optional<Author> findAuthorByName(String name);


}
