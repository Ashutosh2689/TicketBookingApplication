package com.ashutosh.theaterservice.repository;

import com.ashutosh.theaterservice.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    List<Showtime> findByMovieId(String movieId);

    @org.springframework.data.jpa.repository.Query(
        "SELECT s FROM Showtime s WHERE s.movieId = :movieId " +
        "AND FUNCTION('DATE', s.startTime) = :date"
    )
    List<Showtime> findByMovieIdAndDate(String movieId, LocalDate date);
}
