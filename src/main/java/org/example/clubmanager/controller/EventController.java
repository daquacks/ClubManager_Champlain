package org.example.clubmanager.controller;

import org.example.clubmanager.model.Event;
import org.example.clubmanager.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private FirebaseService firebaseService;

    @PostMapping
    public String createEvent(@RequestBody Event event) throws ExecutionException, InterruptedException {
        return firebaseService.createEvent(event);
    }

    @GetMapping
    public List<Event> getAllEvents() throws ExecutionException, InterruptedException {
        return firebaseService.getAllEvents();
    }

    @GetMapping("/club/{clubId}")
    public List<Event> getEventsByClub(@PathVariable String clubId) throws ExecutionException, InterruptedException {
        return firebaseService.getEventsByClub(clubId);
    }
}
