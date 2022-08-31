package com.av.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "Book")
public class Book {
    public static final String FIND_ALL = "Book.findAll";
    public static final String FIND_BY_NAME = "Book.byName";
    private List<Author> authors = new ArrayList<>();
    @Id
    private String title;

    private String isbn;
    private short edition = 1;
    private List<Comment> comments = new ArrayList<>();

    @Override
    public String toString() {
        return "Book{" +
                ", authors=" + authors +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", edition=" + edition +
                ", comments=" + comments +
                '}';
    }
}
