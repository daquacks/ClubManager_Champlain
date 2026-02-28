package org.example.clubmanager.controller;

import org.example.clubmanager.model.Club;
import org.example.clubmanager.model.Key;
import org.example.clubmanager.service.DiscordService;
import org.example.clubmanager.service.FirebaseService;
import org.example.clubmanager.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private DiscordService discordService;

    @PostMapping
    public Club createClub(@RequestBody Club club, @RequestParam String managerKey) throws Exception {
        Key encryptedManagerKey = firebaseService.getKeyByPurpose("manager");
        String MANAGER_KEY = Key.decryptKey(encryptedManagerKey);
        
        if (!MANAGER_KEY.equals(managerKey)) {
            return null;
        }
        firebaseService.createClub(club);
        return club;
    }

    @GetMapping
    public List<Club> getAllClubs() throws ExecutionException, InterruptedException {
        return firebaseService.getAllClubs();
    }

    @GetMapping("/{id}")
    public Club getClub(@PathVariable String id) throws ExecutionException, InterruptedException {
        return firebaseService.getClub(id);
    }

    @PostMapping("/{id}/join")
    public void joinClub(@PathVariable String id, @RequestParam String userId) throws ExecutionException, InterruptedException {
        firebaseService.joinClub(id, userId);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadClubsPdf() throws ExecutionException, InterruptedException, IOException {
        List<Club> clubs = firebaseService.getAllClubs();
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        pdfService.generateClubsPdf(clubs, outputStream);
        
        byte[] pdfBytes = outputStream.toByteArray();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=clubs.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @PostMapping("/{id}/share")
    public ResponseEntity<String> shareClubToDiscord(@PathVariable String id) throws ExecutionException, InterruptedException {
        Club club = firebaseService.getClub(id);
        if (club == null) {
            return ResponseEntity.notFound().build();
        }

        String message = "**Check out this club!**\n" +
                "**Name:** " + club.getName() + "\n" +
                "**Description:** " + club.getDescription() + "\n" +
                "Join us today!";
        
        // Use the provided Discord Webhook URL
        discordService.shareToDiscord("https://discord.com/api/webhooks/1477405400117678082/mFC91DLsW1Xy75JAObXXQ2UPTEqrclTZGXu2fuWqqxhd529beObuibApn1F0MOG6yZKO", message); 

        return ResponseEntity.ok("Shared to Discord!");
    }

    @PutMapping("/clubs/{id}")
    public ResponseEntity<String> updateClub(@PathVariable String id, @RequestBody Club clubUpdate) {
        try {
            Key secureKey = firebaseService.getKeyByPurpose(id);

            if (secureKey == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No key found for this club.");
            }

            String decryptedPassword = Key.decryptKey(secureKey);

            if (!decryptedPassword.equals(clubUpdate.getAdminKey())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Admin Key");
            }

            firebaseService.createClub(clubUpdate);

            return ResponseEntity.ok("Update successful");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
}
