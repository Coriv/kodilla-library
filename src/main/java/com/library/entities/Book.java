package com.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Books")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedNativeQuery(
        name = "Book.findAvailableBooksByTitleID",
        query = "SELECT * FROM Books WHERE status = 0 AND title_Id = :titleId",
        resultClass = Book.class
)
public class Book {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "Book_ID", unique = true)
    private long bookId;

    @Column(name = "Status")
    @NotNull
    private Status status;

    @ManyToOne(cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "Title_ID")
    private Title title;

}
