package com.example.roommanagement.repository;

import com.example.roommanagement.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository
        extends MongoRepository<Movie, String> {
}