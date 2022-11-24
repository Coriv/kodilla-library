package com.library.repository;

import com.library.entities.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BookDao extends CrudRepository<Book, Long> {

    Optional<Book> findByBookId(Long id);

    List<Book> findAll();

    Book save(Book book);

    @Query(nativeQuery = true)
    List<Book> findAvailableBooksByTitleID(@Param("titleId") long titleId);
}
