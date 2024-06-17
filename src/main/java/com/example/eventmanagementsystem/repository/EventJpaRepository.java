package com.example.eventmanagementsystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventmanagementsystem.repository.Event;

@Repository
public interface EventJpaRepository extends JpaRepository<Event, Integer> {
}