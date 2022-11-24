package com.library;

import com.library.entities.*;
import com.library.repository.BookDao;
import com.library.repository.ReaderDao;
import com.library.repository.RentalDao;
import com.library.repository.TitleDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class KodillaLibraryApplicationTests {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private ReaderDao readerDao;
    @Autowired
    private RentalDao rentalDao;
    @Autowired
    private TitleDao titleDao;

    @Test
    void contextLoads() {
    }

    @Test
    void addingToDatabaseTest() {
        //Given
        Title title1 = Title.builder().title("Head First. Java")
                .author("Kathy Skiera")
                .yearOfPublication(2000)
                .build();
        Title title2 = Title.builder().title("A Brief History of Time")
                .author("Stephen Hawking")
                .yearOfPublication(1988)
                .build();
        Title title3 = Title.builder().title("Black Swan")
                .author("Nassim Taleb")
                .yearOfPublication(2007)
                .build();

        Book book1 = Book.builder().status(Status.AVAILABLE).build();
        Book book2 = Book.builder().status(Status.BORROWED).build();
        Book book3 = Book.builder().status(Status.LOST).build();

        book1.setTitle(title1);
        book2.setTitle(title1);
        book3.setTitle(title1);

        Book book4 = Book.builder().status(Status.BORROWED).build();
        Book book5 = Book.builder().status(Status.DESTROYED).build();
        Book book6 = Book.builder().status(Status.AVAILABLE).build();

        book4.setTitle(title2);
        book5.setTitle(title2);
        book6.setTitle(title2);

        Book book7 = Book.builder().status(Status.AVAILABLE).build();
        Book book8 = Book.builder().status(Status.BORROWED).build();
        Book book9 = Book.builder().status(Status.BORROWED).build();

        book7.setTitle(title3);
        book8.setTitle(title3);
        book9.setTitle(title3);

        title1.getListOfBook().addAll(Arrays.asList(book1, book2, book3));
        title2.getListOfBook().addAll(Arrays.asList(book4, book5, book6));
        title3.getListOfBook().addAll(Arrays.asList(book7, book8, book9));

        Reader reader1 = Reader.builder().firstName("John")
                .lastName("Smith")
                .dateOfJoining(LocalDate.of(2022, 11, 23)).build();

        Reader reader2 = Reader.builder().firstName("Dan")
                .lastName("Brown")
                .dateOfJoining(LocalDate.of(2022, 3, 3)).build();

        Reader reader3 = Reader.builder().firstName("John")
                .lastName("Smith")
                .dateOfJoining(LocalDate.of(2022, 11, 23)).build();

        Rental rental1 = Rental.builder().reader(reader1)
                .book(book2)
                .dataOfRental(LocalDate.of(2022, 7, 11))
                .dataOfReturn(LocalDate.of(2022, 12, 24)).build();

        Rental rental2 = Rental.builder().reader(reader2)
                .book(book3)
                .dataOfRental(LocalDate.of(2022, 1, 1))
                .dataOfReturn(LocalDate.of(2022, 4, 22)).build();

        Rental rental3 = Rental.builder().reader(reader2)
                .book(book4)
                .dataOfRental(LocalDate.of(2022, 2, 3))
                .dataOfReturn(LocalDate.of(2022, 5, 29)).build();

        Rental rental4 = Rental.builder().reader(reader3)
                .book(book8)
                .dataOfRental(LocalDate.of(2022, 2, 15))
                .dataOfReturn(LocalDate.of(2022, 7, 15)).build();

        Rental rental5 = Rental.builder().reader(reader3)
                .book(book9)
                .dataOfRental(LocalDate.of(2022, 5, 5))
                .dataOfReturn(LocalDate.of(2022, 9, 2)).build();

        List<Rental> rentalList = Arrays.asList(rental1, rental2, rental3, rental4, rental5);

        //When
        rentalDao.saveAll(rentalList);
        bookDao.saveAll(Arrays.asList(book1, book5, book6, book7));

        List<Book> books = bookDao.findAll();
        List<Reader> readers = readerDao.findAll();
        List<Title> titles = titleDao.findAll();
        List<Rental> rentals = rentalDao.findAll();

        //Then
        Assertions.assertEquals(9, books.size());
        Assertions.assertEquals(3, readers.size());
        Assertions.assertEquals(3, titles.size());
        Assertions.assertEquals(5, rentals.size());

        //CleanUp
        List<Long> bookIdList = books.stream()
                .map(Book::getBookId)
                .collect(Collectors.toList());

        List<Long> rentalIdList = rentalList.stream()
                .map(Rental::getRentId)
                .collect(Collectors.toList());

        List<Long> readerIdList = readers.stream()
                .map(Reader::getReaderId)
                .collect(Collectors.toList());

        List<Long> titleIdList = titles.stream()
                .map(Title::getTitleId)
                .collect(Collectors.toList());

        rentalDao.deleteAllById(rentalIdList);
        bookDao.deleteAllById(bookIdList);
        readerDao.deleteAllById(readerIdList);
        titleDao.deleteAllById(titleIdList);
    }

    @Test
    void lookingForNumbersOfBooksTest() {
        Title title = Title.builder().title("Fight")
                .author("Adam Thomason")
                .yearOfPublication(1999)
                .build();


        Book book1 = Book.builder().status(Status.AVAILABLE).build();
        Book book2 = Book.builder().status(Status.BORROWED).build();

        book1.setTitle(title);
        book2.setTitle(title);

        title.getListOfBook().addAll(Arrays.asList(book1, book2));


        bookDao.save(book1);
        bookDao.save(book2);

        List<Book> quantity = bookDao.findAvailableBooksByTitleID(book1.getTitle().getTitleId());

        Assertions.assertEquals(1, quantity.size());

        bookDao.deleteById(book1.getBookId());
        bookDao.deleteById(book2.getBookId());
        titleDao.deleteById(title.getTitleId());

    }
}
