package com.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Title")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Title {
    @Id
    @GeneratedValue
    @Column(name = "Title_ID", unique = true)
    @NotNull
    private long titleId;

    @NotNull
    @Column(name = "Title")
    private String title;

    @NotNull
    @Column(name = "Author")
    private String author;

    @NotNull
    @Column(name = "Year_Of_Publication")
    private int yearOfPublication;

    @Builder.Default
    @OneToMany(targetEntity = Book.class,
            mappedBy = "title",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    private List<Book> listOfBook = new ArrayList<>();

}
