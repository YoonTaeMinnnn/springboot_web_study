package jpa.demo.controller;

import jpa.demo.domain.item.Book;
import jpa.demo.domain.item.Movie;
import jpa.demo.repository.BookRepository;
import jpa.demo.repository.ItemRepository;
import jpa.demo.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/extend")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ExtendTestController {

    private final ItemRepository itemRepository;
    private final BookRepository bookRepository;
    private final MovieRepository movieRepository;

    @PostMapping()
    public void test(){
        Book book = Book.builder().author("윤태민").isbn("adsad").name("아이템이름").price(100).stockQuantity(100).build();
        Movie movie = Movie.builder().director("봉준호").actor("최민식").name("아이템이름").price(100).stockQuantity(100).build();

        itemRepository.save(book);
        itemRepository.save(movie);
        log.info("items = {}", itemRepository.findAll());
        log.info("books = {}", bookRepository.findAll());
        log.info("movie = {}", movieRepository.findAll());

    }
}
