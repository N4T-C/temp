package com.example.roommanagement.controller;


import com.example.roommanagement.model.Movie;
import com.example.roommanagement.model.Booking;
import com.example.roommanagement.service.ManagementService;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ManagementController {

    private final ManagementService service;

    public ManagementController(ManagementService service) {
        this.service = service;
    }

    // MOVIE CRUD

    @GetMapping("/api/movies")
    public List<Movie> getMovies() {
        return service.getMovies();
    }

    @PostMapping("/api/movies")
    public Movie addMovie(@RequestBody Movie movie) {
        return service.addMovie(movie);
    }

    @PutMapping("/api/movies/{id}")
    public Movie updateMovie(@PathVariable String id,
                             @RequestBody Movie movie) {
        return service.updateMovie(id, movie);
    }

    @DeleteMapping("/api/movies/{id}")
    public String deleteMovie(@PathVariable String id) {
        service.deleteMovie(id);
        return "Movie deleted";
    }

    // BOOKING CRUD

    @GetMapping("/api/bookings")
    public List<Booking> getBookings() {
        return service.getBookings();
    }

    @PostMapping("/api/bookings")
    public Booking addBooking(@RequestBody Booking booking) {
        return service.addBooking(booking);
    }

    @PutMapping("/api/bookings/{id}")
    public Booking updateBooking(@PathVariable String id,
                                 @RequestBody Booking booking) {
        return service.updateBooking(id, booking);
    }

    @DeleteMapping("/api/bookings/{id}")
    public String deleteBooking(@PathVariable String id) {
        service.deleteBooking(id);
        return "Booking deleted";
    }

    // ADDITIONAL API 1: BOOKED MOVIES

    @GetMapping("/api/movies/booked")
    public List<Map<String, Object>> getBookedMovies() {
        return service.getBookedMovies();
    }

    @GetMapping("/api/movies/single")
    public List<Movie> getBookedMovies1() {
        return service.getBookedMovies1();
    }

    // ADDITIONAL API 2: AVAILABLE MOVIES

    @GetMapping("/api/movies/available")
    public List<Movie> getAvailableMovies() {
        return service.getAvailableMovies();
    }

    @PutMapping("/api/bookings/{id}/status")
    public Booking toggleBookingStatus(@PathVariable String id) {
        return service.toggleBookingStatus(id);
    }
}