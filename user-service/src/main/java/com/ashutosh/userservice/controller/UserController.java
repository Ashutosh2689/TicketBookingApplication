package com.ashutosh.userservice.controller;

import com.ashutosh.userservice.model.UserProfile;
import com.ashutosh.userservice.repository.UserProfileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserProfileRepository repository;

    public UserController(UserProfileRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<UserProfile> create(@RequestBody UserProfile profile) {
        return ResponseEntity.ok(repository.save(profile));
    }

    @GetMapping("/{authUserId}")
    public ResponseEntity<UserProfile> getByAuthUserId(@PathVariable Long authUserId) {
        return repository.findByAuthUserId(authUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> update(@PathVariable Long id, @RequestBody UserProfile updated) {
        return repository.findById(id).map(existing -> {
            existing.setFullName(updated.getFullName());
            existing.setPhoneNumber(updated.getPhoneNumber());
            existing.setCity(updated.getCity());
            return ResponseEntity.ok(repository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }
}
