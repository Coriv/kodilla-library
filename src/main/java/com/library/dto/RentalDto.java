package com.library.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RentalDto {
    private long rentId;
    private LocalDate dateOfRental;
    private LocalDate dateOfReturn;
    private long readerId;
    private long bookId;

}
