package com.example.eventmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.ArrayList;

import com.example.eventmanagementsystem.model.Event;
import com.example.eventmanagementsystem.model.Sponsor;
import com.example.eventmanagementsystem.repository.SponsorRepository;
import com.example.eventmanagementsystem.repository.EventJpaRepository;
import com.example.eventmanagementsystem.repository.SponsorJpaRepository;

@Service
public class SponsorJpaService implements SponsorRepository {

    @Autowired
    public SponsorJpaRepository sr;
    @Autowired
    public EventJpaRepository er;

    @Override
    public List<Sponsor> getSponsors() {
        return (ArrayList<Sponsor>) sr.findAll();
    }

    @Override
    public Sponsor getSponsorById(int sponsorId) {
        try {
            Sponsor sponsor = sr.findById(sponsorId).get();
            return sponsor;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Sponsor addSponsor(Sponsor sponsor) {
        List<Integer> eventIds = new ArrayList<>();
        for (Event event : sponsor.getEvents()) {
            eventIds.add(event.getEventId());
        }

        List<Event> events = er.findAllById(eventIds);
        if (eventIds.size() != events.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        sponsor.setEvents(events);
        return sr.save(sponsor);
    }

    @Override
    public Sponsor updateSponsor(int sponsorId, Sponsor sponsor) {
        try {
            Sponsor newSponsor = sr.findById(sponsorId).get();
            if (sponsor.getSponsorName() != null) {
                newSponsor.setSponsorName(sponsor.getSponsorName());
            }
            if (sponsor.getIndustry() != null) {
                newSponsor.setIndustry(sponsor.getIndustry());
            }
            if (sponsor.getEvents() != null) {
                List<Integer> eventIds = new ArrayList<>();
                for (Event event : sponsor.getEvents()) {
                    eventIds.add(event.getEventId());
                }
                List<Event> events = er.findAllById(eventIds);
                if (eventIds.size() != events.size()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
                newSponsor.setEvents(events);
            }
            return sr.save(newSponsor);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteSponsor(int sponsorId) {
        try {
            sr.deleteById(sponsorId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Event> getSponsorEvents(int eventId) {
        try {
            Sponsor sponsor = sr.findById(eventId).get();
            return sponsor.getEvents();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}