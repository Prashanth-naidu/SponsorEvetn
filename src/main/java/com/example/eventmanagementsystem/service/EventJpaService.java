package com.example.eventmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.ArrayList;

import com.example.eventmanagementsystem.model.Event;
import com.example.eventmanagementsystem.model.Sponsor;
import com.example.eventmanagementsystem.repository.EventRepository;
import com.example.eventmanagementsystem.repository.EventJpaRepository;
import com.example.eventmanagementsystem.repository.SponsorJpaRepository;

@Service
public class EventJpaService implements EventRepository {

    @Autowired
    public EventJpaRepository er;

    @Autowired
    public SponsorRepository sr;

    @Override
    public ArrayList<Event> getEvents() {
        return (ArrayList<Event>) er.findAll();
    }

    @Override
    public Event getEventById(int eventId) {
        try {
            Event event = er.findById(eventId).get();
            return event;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Event addEvent(Event event) {
        List<Integer> sponsorIds = new ArrayList<>();
        for (Sponsor sponsor : event.getSponsors()) {
            sponsorIds.add(sponsor.getSponsorId());
        }

        List<Sponsor> sponsors = sr.findAllById(sponsorIds);
        event.setSponsors(sponsors);
        for (Sponsor sponsor : sponsors) {
            sponsor.getEvents().add(event);
        }
        Event savedEvent = er.save(event);
        sr.saveAll(sponsors);

        return savedEvent;
    }

    @Override
    public Event updateEvent(int eventId, Event event) {
        try {
            Event newOne = er.findById(eventId).get();
            if (event.getEventName() != null) {
                newOne.setEventName(event.getEventName());
            }
            if (event.getDate() != null) {
                newOne.setDate(event.getDate());
            }
            if (event.getSponsors() != null) {
                List<Sponsor> sponsors = newOne.getSponsors();
                for (Sponsor sponsor : sponsors) {
                    sponsor.getEvents().remove(newOne);
                }
                sr.saveAll(sponsors);
                List<Sponsor> newSponsorIds = new ArrayList<>();
                for (Sponsor sponsor : event.getSponsors()) {
                    newSponsorIds.add(sponsor.getSponsorId());
                }
                List<Sponsor> newSponsors = sr.findAllById(newSponsorIds);
                for (Sponsor sponsor : newSponsors) {
                    sponsor.getEvents().add(newOne);
                }
                sr.saveAll(newSponsors);
                newOne.setSponsors(newSponsors);

            }
            return sr.saveAll(newSponsors);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteEvent(int eventId) {
        try {
            Event event = er.findById(eventId).get();
            List<Sponsor> sponsors = event.getSponsors();
            for (Sponsor sponsor : sponsors) {
                sponsor.getEvents().remove(event);
            }

            sr.saveAll(sponsors);
            er.deleteById(eventId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Sponsor> getEventSponsor(int eventId) {
        try {
            Event event = er.findById(eventId).get();
            return event.getSponsors();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}