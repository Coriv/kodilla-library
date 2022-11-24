package com.library.GlobalHttoErrorHandler;

import com.library.exception.BookNotFoundException;
import com.library.exception.ReaderNotFoundException;
import com.library.exception.RentalNotFoundException;
import com.library.exception.TitleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> bookNotFoundHandler(BookNotFoundException e) {
        return new ResponseEntity<>("Book does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReaderNotFoundException.class)
    public ResponseEntity<Object> readerNotFoundHandler(ReaderNotFoundException e) {
        return new ResponseEntity<>("Reader does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RentalNotFoundException.class)
    public ResponseEntity<Object> rentalNotFoundHandler(RentalNotFoundException e) {
        return new ResponseEntity<>("Rental does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TitleNotFoundException.class)
    public ResponseEntity<Object> titleNotFoundHandler(TitleNotFoundException e) {
        return new ResponseEntity<>("Title does not exist", HttpStatus.NOT_FOUND);
    }
}
