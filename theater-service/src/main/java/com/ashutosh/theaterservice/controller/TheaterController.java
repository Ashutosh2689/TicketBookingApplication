package com.ashutosh.theaterservice.controller;

import com.ashutosh.theaterservice.model.Theater;
import com.ashutosh.theaterservice.repository.TheaterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {

    private final TheaterRepository repository;

    public TheaterController(TheaterRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Theater> getAll(@RequestParam(required = false) String city) {
        if (city != null) return repository.findByCityIgnoreCase(city);
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theater> getById(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Theater> create(@RequestBody Theater theater) {
        theater.getScreens().forEach(screen -> screen.setTheater(theater));
        return ResponseEntity.ok(repository.save(theater));
    }
}
