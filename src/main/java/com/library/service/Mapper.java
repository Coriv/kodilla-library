package com.library.service;

import com.library.dto.BookDto;
import com.library.dto.ReaderDto;
import com.library.dto.RentalDto;
import com.library.dto.TitleDto;
import com.library.entities.Book;
import com.library.entities.Reader;
import com.library.entities.Rental;
import com.library.entities.Title;
import com.library.exception.BookNotFoundException;
import com.library.exception.ReaderNotFoundException;
import com.library.exception.TitleNotFoundException;
import com.library.repository.BookDao;
import com.library.repository.ReaderDao;
import com.library.repository.TitleDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Mapper {
    private final TitleDao titleDao;
    private final ReaderDao readerDao;
    private final BookDao bookDao;

    public Book mapToBook(BookDto bookDto) throws TitleNotFoundException {
        return Book.builder()
                .bookId(bookDto.getBookId())
                .status(bookDto.getStatus())
                .title(titleDao.findById(bookDto.getBookId()).orElseThrow(TitleNotFoundException::new))
                .build();
    }

    public BookDto mapToBookDto(Book book) {
        return BookDto.builder()
                .bookId(book.getBookId())
                .status(book.getStatus())
                .titleId(book.getBookId())
                .build();
    }

    public Reader mapToReader(ReaderDto readerDto) {
        return Reader.builder()
                .readerId(readerDto.getReaderId())
                .firstName(readerDto.getFirstName())
                .lastName(readerDto.getLastName())
                .dateOfJoining(readerDto.getDateOfJoining())
                .build();
    }

    public ReaderDto mapToReaderDto(Reader reader) {
        return ReaderDto.builder()
                .readerId(reader.getReaderId())
                .firstName(reader.getFirstName())
                .lastName(reader.getLastName())
                .dateOfJoining(reader.getDateOfJoining())
                .build();
    }

    public Title mapToTitle(TitleDto titleDto) {
        return Title.builder()
                .titleId(titleDto.getTitleId())
                .title(titleDto.getTitle())
                .author(titleDto.getAuthor())
                .yearOfPublication(titleDto.getYearOfPublication())
                .build();
    }

    public Rental mapToRental(RentalDto rentalDto) throws ReaderNotFoundException, BookNotFoundException {
        return Rental.builder()
                .rentId(rentalDto.getRentId())
                .dataOfRental(rentalDto.getDateOfRental())
                .dataOfReturn(rentalDto.getDateOfReturn())
                .reader(readerDao.findById(rentalDto.getReaderId()).orElseThrow(ReaderNotFoundException::new))
                .book(bookDao.findById(rentalDto.getBookId()).orElseThrow(BookNotFoundException::new))
                .build();
    }

    public RentalDto mapToRentalDto(Rental rental) {
        return RentalDto.builder()
                .rentId(rental.getRentId())
                .dateOfRental(rental.getDataOfRental())
                .dateOfReturn(rental.getDataOfReturn())
                .readerId(rental.getReader().getReaderId())
                .bookId(rental.getBook().getBookId())
                .build();
    }

    public List<ReaderDto> mapToListReaderDto(List<Reader> readerList) {
        return readerList.stream()
                .map(this::mapToReaderDto)
                .collect(Collectors.toList());
    }
}
