package org.example.clubmanager.controller;

import org.example.clubmanager.model.Club;
import org.example.clubmanager.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    @Autowired
    private FirebaseService firebaseService;

    @PostMapping
    public String createClub(@RequestBody Club club) throws ExecutionException, InterruptedException {
        return firebaseService.createClub(club);
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
}
