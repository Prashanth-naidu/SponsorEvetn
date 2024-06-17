package com.example.eventmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.example.eventmanagementsystem.model.*;
import com.example.eventmanagementsystem.repository.*;
import com.example.eventmanagementsystem.service.*;

@RestController
public class SponsorController {

    @Autowired
    private SponsorJpaService ss;

    @GetMapping("/events/sponsors")
    public ArrayList<Sponsor> getSponsors() {
        return ss.getSponsors();
    }

    @GetMapping("/events/sponsors/{sponsorId}")
    public Sponsor getSponsorById(@PathVariable("sponsorId") int sponsorId) {
        return ss.getSponsorById(sponsorId);
    }

    @PostMapping("/events/sponsors")
    public Sponsor addSponsor(@RequestBody Sponsor sponsor) {
        return ss.addSponsor(sponsor);
    }

    @PutMapping("/events/sponsors/{sponsorId}")
    public Sponsor updateSponsor(@PathVariable("sponsorId") int sponsorId, @RequestBody Sponsor sponsor) {
        return ss.updateSponsor(sponsorId, sponsor);
    }

    @DeleteMapping("/events/sponsors/{sponsorId}")
    public void deleteSponsor(@PathVariable("sponsorId") int sponsorId) {
        return ss.deleteSponsor(sponsorId);
    }

    @GetMapping("/events/{eventId}/sponsors")
    public List<Event> getSponsorEvents(@PathVariable("eventId") int eventId) {
        return ss.getSponsorEvents(eventId);
    }
}