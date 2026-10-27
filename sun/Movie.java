package com.example.roommanagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "movie")
public class Movie {
    @Id
    private String id;

    private String title;
    private int totalSeats;
    private int availableSeats;
}