package com.example.tinderaibackend.profiles;

public record Profile(
        String id,
        String firstName,
        String lastname,
        Integer age,
        String ethnicity,
        Gender gender,
        String bio,
        String imageUrl,
        String myersBriggsPersonalityType
) {
}
