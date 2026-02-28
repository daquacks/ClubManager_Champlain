package org.example.clubmanager;

import org.example.clubmanager.model.Club;
import org.example.clubmanager.model.Event;
import org.example.clubmanager.model.Key;
import org.example.clubmanager.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

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
			Map<String, String> clubDescriptions = new HashMap<>();
            clubDescriptions.put("Anime", "Dive into the world of Japanese animation! We screen popular series, discuss manga, and organize cosplay events. Whether you're a seasoned otaku or just curious, join us for weekly screenings and fun discussions.");
            clubDescriptions.put("Art", "Unleash your creativity! The Art Club provides a space for students to explore various mediums, from sketching and painting to digital art. We host workshops, collaborative projects, and gallery showcases.");
            clubDescriptions.put("BTW", "Join the conversation! BTW is a club for open discussion, social connection, and student engagement. A great place to meet new people and share your perspective.");
            clubDescriptions.put("CCMUN", "Champlain College Model United Nations. Step into the shoes of world leaders! We simulate UN committees to debate global issues, draft resolutions, and develop public speaking and negotiation skills.");
            clubDescriptions.put("Code#", "For those who love to code! We work on collaborative software projects, participate in hackathons, and learn new programming languages and frameworks together. Beginners and experts welcome.");
            clubDescriptions.put("Debate", "Sharpen your critical thinking and argumentation skills. We debate a wide range of topics, from current events to philosophical dilemmas, in a structured and respectful environment.");
            clubDescriptions.put("Finance", "Learn about investing, stock markets, and personal finance. We host guest speakers, run stock market simulations, and discuss economic trends to help you manage your money better.");
            clubDescriptions.put("FLIP", "Flip the script on leadership! We focus on personal development, team building, and student empowerment through workshops and community projects.");
            clubDescriptions.put("Frisbee", "Get active with Ultimate Frisbee! We play casual pickup games and compete against other schools. It's a great way to stay fit, have fun, and enjoy the outdoors.");
            clubDescriptions.put("Gaming", "The ultimate hub for gamers. We host tournaments for League of Legends, Smash Bros, Valorant, and more. Come play casually or compete for glory!");
            clubDescriptions.put("Green Team", "Dedicated to sustainability and environmental awareness. We organize campus cleanups, recycling initiatives, and educational campaigns to make our school greener.");
            clubDescriptions.put("Health & Wellness", "Promoting physical and mental well-being. We offer yoga sessions, meditation workshops, and discussions on healthy living, nutrition, and stress management.");
            clubDescriptions.put("MedLife", "For students interested in medicine and healthcare. We explore medical careers, organize volunteer trips, and raise awareness about global health issues.");
            clubDescriptions.put("MSA", "Muslim Student Association. A welcoming community for Muslim students and allies. We host prayer sessions, cultural events, and discussions to foster understanding and brotherhood.");
            clubDescriptions.put("Music Club", "Jam out with fellow musicians! Whether you play an instrument or sing, join us for jam sessions, open mic nights, and performances at school events.");
            clubDescriptions.put("Physics", "Explore the mysteries of the universe. We discuss physics concepts, conduct fun experiments, and watch sci-fi movies. Perfect for anyone curious about how the world works.");
            clubDescriptions.put("CCC", "Connect, Create, Collaborate. CCC is a hub for student creativity, community projects, and social events. Come see what we're all about!");
            clubDescriptions.put("Programming", "Focusing on the fundamentals of computer science. We offer tutoring, coding challenges, and workshops on algorithms and data structures.");
            clubDescriptions.put("Robotics", "Build and program robots! We work on hands-on projects, learn about electronics and mechanics, and compete in robotics competitions.");
            clubDescriptions.put("Tabletop", "Roll for initiative! We play Dungeons & Dragons, board games, and card games. A great place to immerse yourself in fantasy worlds and strategy games.");
            clubDescriptions.put("SHINE", "Shine bright! We are dedicated to community service, volunteering, and making a positive impact on campus and in the local community.");
            clubDescriptions.put("Newspaper Club", "The voice of the students. We write articles, take photos, and publish the school newspaper. Cover campus news, opinions, and creative writing.");
            clubDescriptions.put("Latino Club", "Celebrating Latino culture! We host dance workshops, food tastings, and cultural events to share the richness of Latin American heritage.");
            clubDescriptions.put("Research Club", "For students interested in academic research. We learn about research methodologies, review scientific papers, and work on independent research projects.");
            clubDescriptions.put("CFD", "A welcoming community for fellowship and discussion. We explore personal growth, shared values, and support one another in a friendly environment.");

            // Map of generic Discord invite links for demo purposes
            Map<String, String> discordLinks = new HashMap<>();
            discordLinks.put("Anime", "https://discord.gg/anime");
            discordLinks.put("Art", "https://discord.gg/art");
            discordLinks.put("Gaming", "https://discord.gg/gaming");
            discordLinks.put("Programming", "https://discord.gg/programming");
            discordLinks.put("Music Club", "https://discord.gg/music");
            discordLinks.put("Robotics", "https://discord.gg/robotics");
            discordLinks.put("Tabletop", "https://discord.gg/dnd");
            discordLinks.put("Finance", "https://discord.gg/investing");
            discordLinks.put("Debate", "https://discord.gg/debate");
            // Default link for others
            String defaultDiscord = "https://discord.gg/champlain";

			// Check if clubs already exist to avoid duplicates
			List<Club> existingClubs = firebaseService.getAllClubs();
			if (existingClubs.isEmpty()) {
				System.out.println("Initializing clubs...");
				for (Map.Entry<String, String> entry : clubDescriptions.entrySet()) {
					Club club = new Club();
					club.setName(entry.getKey());
                    // Use the first sentence as the overview
                    String description = entry.getValue();
                    String overview = description.contains(".") ? description.substring(0, description.indexOf(".") + 1) : description;
                    
                    club.setOverview(overview);
					club.setDescription(description);

                    club.setAdminKey("none");
                    
                    // Set Discord link if available, otherwise default
                    club.setDiscordLink(discordLinks.getOrDefault(entry.getKey(), defaultDiscord));

					firebaseService.createClub(club);
					System.out.println("Created club: " + entry.getKey());
				}
			} else {
				System.out.println("Clubs already initialized.");
			}

            if (firebaseService.getKeyByPurpose("manager") == null) {
                Key managerKey = new Key();
                managerKey.encryptAndSetKey("F9485D439D87A5FA");
                managerKey.setPurpose("manager");
                firebaseService.createKey(managerKey);
                System.out.println("Added manager key to database.");
            } else {
                System.out.println("Manager key already exists.");
            }
            
            
            // Initialize Mock Events
            List<Event> existingEvents = firebaseService.getAllEvents();
            if (existingEvents.isEmpty()) {
                System.out.println("Initializing mock events...");
                List<Club> clubs = firebaseService.getAllClubs();
                if (!clubs.isEmpty()) {
                    // Create a few events for random clubs
                    Random rand = new Random();
                    Calendar calendar = Calendar.getInstance();
                    
                    for (int i = 0; i < 10; i++) {
                        Club randomClub = clubs.get(rand.nextInt(clubs.size()));
                        
                        calendar.add(Calendar.DAY_OF_MONTH, rand.nextInt(14) + 1); // Random date in next 2 weeks
                        Date startTime = calendar.getTime();
                        calendar.add(Calendar.HOUR, 2);
                        Date endTime = calendar.getTime();
                        
                        Event event = new Event();
                        event.setClubId(randomClub.getId());
                        event.setTitle(randomClub.getName() + " Weekly Meeting");
                        event.setDescription("Join us for our weekly gathering to discuss upcoming projects and hang out!");
                        event.setStartTime(startTime);
                        event.setEndTime(endTime);
                        event.setLocation("Room " + (100 + rand.nextInt(200)));
                        
                        firebaseService.createEvent(event);
                        System.out.println("Created event for: " + randomClub.getName());
                    }

                    // Create specific event for Programming Club
                    Club programmingClub = clubs.stream()
                            .filter(c -> "Programming".equals(c.getName()))
                            .findFirst()
                            .orElse(null);

                    if (programmingClub != null) {
                        Calendar codeQuestCal = Calendar.getInstance();
                        // Set to TODAY
                        codeQuestCal.set(Calendar.HOUR_OF_DAY, 18); // 6 PM
                        codeQuestCal.set(Calendar.MINUTE, 30);      // 30 min
                        codeQuestCal.set(Calendar.SECOND, 0);
                        codeQuestCal.set(Calendar.MILLISECOND, 0);

                        Date startTime = codeQuestCal.getTime();
                        codeQuestCal.add(Calendar.HOUR, 2); // 2 hours long
                        Date endTime = codeQuestCal.getTime();

                        Event codeQuest = new Event();
                        codeQuest.setClubId(programmingClub.getId());
                        codeQuest.setTitle("CodeQuest 3 Presentations");
                        codeQuest.setDescription("Showcase your projects from the CodeQuest 3 hackathon! Pizza and drinks provided.");
                        codeQuest.setStartTime(startTime);
                        codeQuest.setEndTime(endTime);
                        codeQuest.setLocation("Main Auditorium");

                        firebaseService.createEvent(codeQuest);
                        System.out.println("Created CodeQuest 3 event for Programming Club");
                    }
                }
            }
		};
	}
}
