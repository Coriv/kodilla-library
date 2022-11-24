package com.library.service;

import com.library.entities.*;
import com.library.exception.BookNotFoundException;
import com.library.exception.ReaderNotFoundException;
import com.library.exception.RentalNotFoundException;
import com.library.repository.BookDao;
import com.library.repository.ReaderDao;
import com.library.repository.RentalDao;
import com.library.repository.TitleDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class DbService {
    private BookDao bookDao;
    private ReaderDao readerDao;
    private RentalDao rentalDao;
    private TitleDao titleDao;

    public Title saveTitle(Title title) {
        return titleDao.save(title);
    }

    public void saveBook(Book book) {
        bookDao.save(book);
    }

    public Book updateBookStatus(long bookId, Status status) throws BookNotFoundException {
        Book book = bookDao.findById(bookId).orElseThrow(BookNotFoundException::new);
        book.setStatus(status);
        return book;
    }

    public Book findBookById(Long id) throws BookNotFoundException {
        return bookDao.findByBookId(id).orElseThrow(BookNotFoundException::new);
    }

    public int countBooksByTitleId(long titleId) {
        return bookDao.findAvailableBooksByTitleID(titleId).size();
    }

    public void rentBook(long readerId, long bookId) throws ReaderNotFoundException, BookNotFoundException {
        Rental rental = Rental.builder()
                .dataOfRental(LocalDate.now())
                .reader(readerDao.findById(readerId).orElseThrow(ReaderNotFoundException::new))
                .book(bookDao.findById(bookId).orElseThrow(BookNotFoundException::new))
                .build();
        rentalDao.save(rental);
    }

    public Rental returnBook(long rentalId) throws RentalNotFoundException {
        Rental rental = rentalDao.findById(rentalId).orElseThrow(RentalNotFoundException::new);
        rental.setDataOfReturn(LocalDate.now());
        return rentalDao.save(rental);
    }

    public List<Reader> allReaders() {
        return readerDao.findAll();
    }

    public Reader addReader(Reader reader) {
        return readerDao.save(reader);
    }
}
