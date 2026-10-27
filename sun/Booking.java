package com.example.roommanagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "booking")
public class Booking {

    @Id
    private String id;

    private String movieId;
    private String customerName;
    private int numberOfTickets;
    private double price;

    private String status = "booked";  // NEW FIELD
}