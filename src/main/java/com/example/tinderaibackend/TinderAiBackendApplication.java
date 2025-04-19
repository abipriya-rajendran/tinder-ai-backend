package com.example.tinderaibackend;

import com.example.tinderaibackend.conversation.ConversationRepository;
import com.example.tinderaibackend.matches.MatchRepository;
import com.example.tinderaibackend.profiles.ProfileCreationService;
import com.example.tinderaibackend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileCreationService profileCreationService;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private MatchRepository matchRepository;
	@Autowired
	private ConversationRepository conversationRepository;

	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}

	@Override
	public void run(String... args) {
		clearAllData();
		profileCreationService.saveProfilesToDB();
	}

	private void clearAllData() {
		profileRepository.deleteAll();
		matchRepository.deleteAll();
		conversationRepository.deleteAll();
	}

}
