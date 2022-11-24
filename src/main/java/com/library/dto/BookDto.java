package com.library.dto;

import com.library.entities.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {
    private long bookId;
    private Status status;
    private long titleId;
}
