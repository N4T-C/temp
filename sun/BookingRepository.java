package com.example.roommanagement.repository;


import com.example.roommanagement.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository
        extends MongoRepository<Booking, String> {
}