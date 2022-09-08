package com.av.cli;

import com.av.dao.AuthorRepository;
import com.av.dao.BookRepository;
import com.av.domain.Author;
import com.av.domain.Book;
import com.av.domain.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@Slf4j
public class DefinerCommand {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;


    public DefinerCommand(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;

    }

    @ShellMethod("add author")
    public void add_author(String name) {
        authorRepository.save(new Author(name));
    }


    @ShellMethod("show authors")
    public void show_authors() {
        authorRepository.findAll().forEach(a -> log.info(MessageFormat.format("author:{0}", a)));
    }

    @ShellMethod("update author name")
    public void update_author_name(String oldName, String newName) {
        authorRepository.findAuthorByName(oldName).ifPresent(
                oldAuthor->
                {
                    authorRepository.delete(oldAuthor);
                    oldAuthor.setName(newName);
                    authorRepository.save(oldAuthor);
                }
        );
    }

    @ShellMethod("create new book")
    public void add_book(String title, short edition, String isbn) {
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setEdition(edition);
        newBook.setIsbn(isbn);
        bookRepository.save(newBook);
    }

    @ShellMethod("show all books")
    public void show_books() {
        bookRepository.findAll().forEach(book -> log.info(MessageFormat.format("Book:{0}", book)));
    }




    @ShellMethod("add comment")
    public void add_comment(String bookTitle, String commentAuthor, String commentData) {
        bookRepository.findBooksByTitle(bookTitle).stream().findFirst().ifPresentOrElse(
                book -> {
                    var comment = new Comment();
                    comment.setText(commentData);
                    comment.setUserName(commentAuthor);
                    book.getComments().add(comment);
                    bookRepository.save(book);
                },
                () -> log.error("You mast init book first")
        );
    }


    @ShellMethod("show comment")
    public void show_comment(String bookTitle) {
        bookRepository.findBooksByTitle(bookTitle).stream().findFirst().ifPresentOrElse(
                book -> log.info(MessageFormat.format("Book.comment:{0}", book.getComments().stream().map(b -> b.getText()).collect(Collectors.joining(",")))),
                () -> log.error("You mast init book & comment")
        );
    }

    @ShellMethod("add genre")
    public void add_genre(String bookTitle, String genre) {
        bookRepository.findBooksByTitle(bookTitle).stream().findFirst().ifPresentOrElse(
                book -> {
                    book.getGenres().add(genre);
                    bookRepository.save(book);
                },
                () -> log.error("You mast init book first")
        );
    }


    @ShellMethod("show genre")
    public void show_genre(String bookTitle) {
        bookRepository.findBooksByTitle(bookTitle).stream().findFirst().ifPresentOrElse(
                book -> log.info(MessageFormat.format("Book.comment:{0}", book.getGenres().stream().collect(Collectors.joining(",")))),
                () -> log.error("You mast init book & comment")
        );
    }


    @ShellMethod("set author for  book")
    public void set_author_to_book(String bookTitle, String authorName) {
        bookRepository.findBooksByTitle(bookTitle).stream().findFirst().ifPresentOrElse(
                book -> {
                    authorRepository.findAuthorByName(authorName).ifPresentOrElse(
                            author ->
                            {
                                author.getBooks().add(book);
                                authorRepository.save(author);
                                book.getAuthors().add(author);
                                bookRepository.save(book);
                            },
                            () -> log.error("You mast init new author first")
                    );
                },
                () -> log.error("You mast init new book first")

        );
    }


    @ShellMethod("delete author for  book")
    public void delete_author_to_book(String bookTitle, String authorName) {
        bookRepository.findBooksByTitle(bookTitle).stream().findFirst().ifPresentOrElse(
                book -> {
                    authorRepository.findAuthorByName(authorName).ifPresentOrElse(
                            author ->
                            {
                                author.getBooks().remove(book);
                                authorRepository.save(author);
                                book.getAuthors().remove(author);
                                bookRepository.save(book);
                            },
                            () -> log.error("You mast init new author first")
                    );
                },
                () -> log.error("You mast init new book first")

        );
    }

}
