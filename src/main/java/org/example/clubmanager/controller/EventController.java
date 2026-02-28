package org.example.clubmanager.controller;

import org.example.clubmanager.model.Event;
import org.example.clubmanager.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private CalendarService calendarService;

    @PostMapping
    public ResponseEntity<String> createEvent(@RequestBody Event event) {
        try {
            String eventId = calendarService.createEvent(event);
            return ResponseEntity.ok(eventId);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<List<Event>> getEventsForClub(@PathVariable String clubId) {
        try {
            return ResponseEntity.ok(calendarService.getEventsForClub(clubId));
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
