package com.example.eventmanagementsystem.model;

import com.example.eventmanagementsystem.model.Event;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sponsor")
public class Sponsor {
    @Id
    @Column(name = "sponsorid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sponsorId;

    @Column(name = "sponsorname")
    private String sponsorName;

    @Column(name = "industry")
    private String industry;

    @ManyToMany
    @JoinTable(name = "event_sponsor", joinColumns = @JoinColumn(name = "sponsorid"), inverseJoinColumns = @JoinColumn(name = "eventid"))
    @JsonIgnoreProperties("sponsors")
    private List<Event> events;

    public Sponsor() {
    }

    public int getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(int sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}