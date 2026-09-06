package com.ashutosh.theaterservice.dto;

public record SeatLockValidationResponse(
        Long showtimeId,
        boolean available,
        int remainingSeats
) {}
