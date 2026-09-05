package com.ashutosh.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Mirrors the User.id issued by auth-service — kept in sync via the user-events Kafka topic in a fuller build. */
    @Column(nullable = false, unique = true)
    private Long authUserId;

    @Column(nullable = false)
    private String email;

    private String fullName;
    private String phoneNumber;
    private String city;
}
