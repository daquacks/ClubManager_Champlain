package org.example.clubmanager;

import org.example.clubmanager.model.Club;
import org.example.clubmanager.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ClubManagerApplication {

	@Autowired
	private FirebaseService firebaseService;

	public static void main(String[] args) {
		SpringApplication.run(ClubManagerApplication.class, args);
	}

	@Bean
	public CommandLineRunner initClubs() {
		return args -> {
			List<String> clubNames = Arrays.asList(
				"Anime", "Art", "BTW", "CCMUN", "Code#", "Debate", "Finance", "FLIP", "Frisbee", 
				"Gaming", "Green Team", "Health & Wellness", "MedLife", "MSA", "Music Club", 
				"Physics", "CCC", "Programming", "Robotics", "Tabletop", "SHINE", "Newspaper Club", 
				"Latino Club", "Research Club", "CFD"
			);

			// Check if clubs already exist to avoid duplicates
			List<Club> existingClubs = firebaseService.getAllClubs();
			if (existingClubs.isEmpty()) {
				System.out.println("Initializing clubs...");
				for (String name : clubNames) {
					Club club = new Club();
					club.setName(name);
                    club.setOverview("Club Overview");
					club.setDescription("Welcome to the " + name + " club!");
					firebaseService.createClub(club);
					System.out.println("Created club: " + name);
				}
			} else {
				System.out.println("Clubs already initialized.");
			}
		};
	}
}
