package com.library.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ReaderDto {
    private long readerId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfJoining;

}
