package com.library.controller;


import com.library.dto.BookDto;
import com.library.dto.ReaderDto;
import com.library.dto.RentalDto;
import com.library.dto.TitleDto;
import com.library.entities.*;
import com.library.exception.BookNotFoundException;
import com.library.exception.ReaderNotFoundException;
import com.library.exception.RentalNotFoundException;
import com.library.exception.TitleNotFoundException;
import com.library.service.DbService;
import com.library.service.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/library")
@AllArgsConstructor
public class LibraryController {

    private Mapper mapper;
    private DbService dbService;

    @GetMapping(value = "/readers")
    public ResponseEntity<List<ReaderDto>> getReaders() {
        List<Reader> readerList = dbService.allReaders();
        List<ReaderDto> readerDtoList = mapper.mapToListReaderDto(readerList);
        return ResponseEntity.ok(readerDtoList);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            value = "/readers")
    public ResponseEntity<Void> addReader(@RequestBody ReaderDto readerDto) {
        Reader reader = mapper.mapToReader(readerDto);
        dbService.addReader(reader);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            value = "/titles")
    public ResponseEntity<Void> addTitle(@RequestBody TitleDto titleDto) {
        Title title = mapper.mapToTitle(titleDto);
        dbService.saveTitle(title);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            value = "/books")
    public ResponseEntity<Void> addBook(@RequestBody BookDto bookDto) throws TitleNotFoundException {
        Book book = mapper.mapToBook(bookDto);
        dbService.saveBook(book);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/books")
    public ResponseEntity<BookDto> updateBookStatus(@RequestParam long bookId, @RequestParam Status status) throws BookNotFoundException {
        Book savedBook = dbService.updateBookStatus(bookId, status);
        return ResponseEntity.ok(mapper.mapToBookDto(savedBook));
    }

    @PostMapping(value = "/rent")
    public ResponseEntity<RentalDto> rentBook(@RequestParam long readerId, @RequestParam long bookId) throws ReaderNotFoundException, BookNotFoundException {
        dbService.rentBook(readerId, bookId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/rent")
    public ResponseEntity<RentalDto> returnBook(@RequestParam long rentalId) throws RentalNotFoundException {
        Rental rental = dbService.returnBook(rentalId);
        return ResponseEntity.ok(mapper.mapToRentalDto(rental));
    }

    @GetMapping(value = "/books")
    public ResponseEntity<Long> howManyBooksAvailable(@RequestParam long i) {
        long books = dbService.countBooksByTitleId(i);
        return ResponseEntity.ok(books);
    }
}
