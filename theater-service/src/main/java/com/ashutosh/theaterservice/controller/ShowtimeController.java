package com.ashutosh.theaterservice.controller;

import com.ashutosh.theaterservice.dto.SeatLockValidationResponse;
import com.ashutosh.theaterservice.model.Showtime;
import com.ashutosh.theaterservice.repository.ShowtimeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {

    private final ShowtimeRepository repository;

    public ShowtimeController(ShowtimeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Showtime> search(@RequestParam String movieId,
                                  @RequestParam(required = false) LocalDate date) {
        if (date != null) return repository.findByMovieIdAndDate(movieId, date);
        return repository.findByMovieId(movieId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Showtime> getById(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Showtime> create(@RequestBody Showtime showtime) {
        return ResponseEntity.ok(repository.save(showtime));
    }

    /** Called by booking-service (Feign) to confirm seats are still available before finalizing a booking. */
    @GetMapping("/{id}/availability")
    public ResponseEntity<SeatLockValidationResponse> checkAvailability(@PathVariable Long id) {
        Showtime showtime = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Showtime not found: " + id));
        int remaining = showtime.getTotalSeats() - showtime.getBookedSeats();
        return ResponseEntity.ok(new SeatLockValidationResponse(id, remaining > 0, remaining));
    }

    /** Called by booking-service after a booking is confirmed, to increment the booked-seat counter. */
    @PostMapping("/{id}/confirm-seats")
    public ResponseEntity<Showtime> confirmSeats(@PathVariable Long id, @RequestParam int seatCount) {
        Showtime showtime = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Showtime not found: " + id));
        showtime.setBookedSeats(showtime.getBookedSeats() + seatCount);
        return ResponseEntity.ok(repository.save(showtime));
    }
}
