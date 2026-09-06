package com.ashutosh.movieservice.repository;

import com.ashutosh.movieservice.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findByGenresContainingIgnoreCase(String genre);
    List<Movie> findByTitleContainingIgnoreCase(String title);
}
