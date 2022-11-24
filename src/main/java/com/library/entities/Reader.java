package com.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "Readers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reader {

    @Id
    @GeneratedValue
    @Column(name = "Reader_ID", unique = true)
    @NotNull
    private long readerId;

    @Column(name = "First_Name")
    @NotNull
    private String firstName;

    @Column(name = "Last_Name")
    @NotNull
    private String lastName;

    @Column(name = "Date_Of_Joining")
    @NotNull
    private LocalDate dateOfJoining;
}
