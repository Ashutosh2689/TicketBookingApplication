package com.ashutosh.theaterservice.repository;

import com.ashutosh.theaterservice.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
}
