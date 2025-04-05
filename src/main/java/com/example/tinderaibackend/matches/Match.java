package com.example.tinderaibackend.matches;

import com.example.tinderaibackend.profiles.Profile;

public record Match(String id, Profile profile, String conversationId) {
}
