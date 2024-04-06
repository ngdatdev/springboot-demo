package com.nguyendat.blog.payload.response;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
public class PostBlogResponse {
    private String title;
    private String body;

    private String chapter;
    private String lastModified;

    public PostBlogResponse(String title, String body, String chapter,LocalDate lastModified) {
        this.title = title;
        this.body = body;
        convertInstantToString(lastModified);
    }

    private void convertInstantToString(LocalDate time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        lastModified = time.format(formatter);
    }
}
