package com.example.roommanagement.service;


import com.example.roommanagement.model.Movie;
import com.example.roommanagement.model.Booking;
import com.example.roommanagement.repository.MovieRepository;
import com.example.roommanagement.repository.BookingRepository;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ManagementService {

    private final MovieRepository movieRepo;
    private final BookingRepository bookingRepo;

    public ManagementService(MovieRepository movieRepo,
                             BookingRepository bookingRepo) {
        this.movieRepo = movieRepo;
        this.bookingRepo = bookingRepo;
    }

    // MOVIE CRUD

    public List<Movie> getMovies() {
        return movieRepo.findAll();
    }

    public Movie addMovie(Movie movie) {
        movie.setId(null);
        return movieRepo.save(movie);
    }

    public Movie updateMovie(String id, Movie updated) {
        Movie movie = movieRepo.findById(id).orElse(null);

        if (movie == null) return null;

        movie.setTitle(updated.getTitle());
        movie.setTotalSeats(updated.getTotalSeats());
        movie.setAvailableSeats(updated.getAvailableSeats());

        return movieRepo.save(movie);
    }

    public void deleteMovie(String id) {
        movieRepo.deleteById(id);
    }

    // BOOKING CRUD

    public List<Booking> getBookings() {
        return bookingRepo.findAll();
    }

    public Booking addBooking(Booking booking) {
        booking.setId(null);
        if (booking.getStatus() == null ||
                booking.getStatus().isBlank()) {
            booking.setStatus("booked");
        }
        return bookingRepo.save(booking);
    }

    public Booking toggleBookingStatus(String id) {

        Booking booking = bookingRepo.findById(id).orElse(null);

        if (booking == null) {
            return null;
        }

        if ("booked".equalsIgnoreCase(booking.getStatus())) {
            booking.setStatus("cancel");
        } else {
            booking.setStatus("booked");
        }

        return bookingRepo.save(booking);
    }

    public Booking updateBooking(String id, Booking updated) {
        Booking booking = bookingRepo.findById(id).orElse(null);

        if (booking == null) return null;

        booking.setMovieId(updated.getMovieId());
        booking.setCustomerName(updated.getCustomerName());
        booking.setNumberOfTickets(updated.getNumberOfTickets());
        booking.setPrice(updated.getPrice());
        booking.setStatus(
                updated.getStatus() == null
                        ? booking.getStatus()
                        : updated.getStatus()
        );

        return bookingRepo.save(booking);
    }

    public void deleteBooking(String id) {
        bookingRepo.deleteById(id);
    }

    // API 1: GET MOVIES THAT HAVE BEEN BOOKED

    public List<Map<String, Object>> getBookedMovies() {

        List<Map<String, Object>> result = new ArrayList<>();

        for (Booking booking : bookingRepo.findAll()) {

            if (booking.getStatus() != null &&
                    !booking.getStatus().equalsIgnoreCase("booked")) {
                continue;
            }
            Movie movie = movieRepo.findById(
                    booking.getMovieId()
            ).orElse(null);

            if (movie != null) {
                Map<String, Object> item = new HashMap<>();

                item.put("booking", booking);
                item.put("movie", movie);

                result.add(item);
            }
        }

        return result;
    }

    public List<Movie> getBookedMovies1() {
        Set<String> seen = new LinkedHashSet<>();
        List<Movie> result = new ArrayList<>();

        for (Booking b : bookingRepo.findAll()) {

            if (b.getStatus() != null &&
                    !b.getStatus().equalsIgnoreCase("booked")) {
                continue;
            }

            String movieId = b.getMovieId();

            if (movieId != null && seen.add(movieId)) {
                movieRepo.findById(movieId).ifPresent(result::add);
            }
        }

        return result;
    }

    // API 2: GET MOVIES WITH AVAILABLE SEATS

    public List<Movie> getAvailableMovies() {

        List<Movie> result = new ArrayList<>();

        for (Movie movie : movieRepo.findAll()) {
            if (movie.getAvailableSeats() > 0) {
                result.add(movie);
            }
        }

        return result;
    }
}