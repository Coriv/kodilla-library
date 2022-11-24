package com.library.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TitleDto {
    private long titleId;
    private String title;
    private String author;
    private int yearOfPublication;
}
