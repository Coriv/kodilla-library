package com.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "Rental")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Rental {

    @Id
    @GeneratedValue
    @Column(name = "RentID", unique = true)
    @NotNull
    private long rentId;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "readerID")
    private Reader reader;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "bookID")
    private Book book;

    @Column(name = "DateOfRental")
    @NotNull
    private LocalDate dataOfRental;

    @Column(name = "DateOfReturn")
    private LocalDate dataOfReturn;
}
