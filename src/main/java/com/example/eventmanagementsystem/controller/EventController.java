package com.example.eventmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.example.eventmanagementsystem.service.EventJpaService;
import com.example.eventmanagementsystem.model.*;
import com.example.eventmanagementsystem.service.*;

@RestController
public class EventController {

    @Autowired
    private EventJpaService es;

    @GetMapping("/events")
    public ArrayList<Event> getEvents() {
        return es.getEvents();
    }

    @GetMapping("/events/{eventId}")
    public Event getEventById(@PathVariable("eventId") int eventId) {
        return es.getEventById(eventId);
    }

    @PostMapping("/events")
    public Event addEvent(@RequestBody Event event) {
        return es.addEvent(event);
    }

    @PutMapping("/events/{eventId}")
    public Event updateEvent(@PathVariable("eventId") int eventId, @RequestBody Event event) {
        return es.updateEvent(eventId, event);
    }

    @DeleteMapping("/events/{eventId}")
    public void deleteEvent(@PathVariable("eventId") int eventId) {
        es.deleteEvent(eventId);
    }

    @GetMapping("/events/{eventId}/sponsors")
    public List<Event> getEventSponsor(@PathVariable("eventId") int eventId) {
        return es.getEventSponsor(eventId);
    }
}