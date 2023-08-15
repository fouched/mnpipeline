package za.co.limehouse.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import za.co.limehouse.model.Book;

import java.util.ArrayList;
import java.util.List;

@Controller("/books")
public class BookController {

    @Get()
    @Produces(MediaType.APPLICATION_JSON) // this is actually the default so not really needed
    public List<Book> index() {

        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setIsbn("12345");
        book.setTitle("Fouche se boek!! 13");
        books.add(book);

        return books;
    }
}
