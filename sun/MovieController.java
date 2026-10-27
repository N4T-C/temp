package com.example.demo;

import java.util.List;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:5173")
public class MovieController {

    private final MovieRepository repo;
    private final MongoTemplate mongoTemplate;

    public MovieController(MovieRepository repo, MongoTemplate mongoTemplate) {
        this.repo = repo;
        this.mongoTemplate = mongoTemplate;
    }

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        movie.setId(null);
        return repo.save(movie);
    }

    @GetMapping
    public List<Movie> getMovies() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable String id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(
            @PathVariable String id, @RequestBody Movie movie) {

        if (!repo.existsById(id))
            return ResponseEntity.notFound().build();

        movie.setId(id);
        return ResponseEntity.ok(repo.save(movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        if (!repo.existsById(id))
            return ResponseEntity.notFound().build();

        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Question-specific operation: fully booked movies
    @GetMapping("/fully-booked")
    public List<Movie> getFullyBookedMovies() {
        return repo.findByAvailableSeats(0);
    }

    // Aggregation: join movies with bookings
    @GetMapping("/with-bookings")
    public List<Document> getMoviesWithBookings() {

        LookupOperation lookup =
                lookup("bookings", "movieId", "movieId", "bookingDetails");

        Aggregation aggregation = newAggregation(lookup);

        AggregationResults<Document> results =
                mongoTemplate.aggregate(
                        aggregation, "movies", Document.class);

        return results.getMappedResults();
    }

    // Aggregation: movies with a booking status of Available
    @GetMapping("/available-bookings")
    public List<Document> getMoviesWithAvailableBookings() {

        Aggregation aggregation = newAggregation(
                lookup("bookings", "movieId", "movieId", "bookingDetails"),
                match(org.springframework.data.mongodb.core.query.Criteria
                        .where("bookingDetails.status").is("Available"))
        );

        AggregationResults<Document> results =
                mongoTemplate.aggregate(
                        aggregation, "movies", Document.class);

        return results.getMappedResults();
    }
}