package com.gov.lunchsession.controller;

import com.gov.lunchsession.dto.RestaurantDTO;
import com.gov.lunchsession.entity.Restaurant;
import com.gov.lunchsession.entity.Session;
import com.gov.lunchsession.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public List<Session> getAllSessions() {
        return sessionService.getAllSessions();
    }

    @GetMapping("/{id}")
    public Session getSessionById(@PathVariable Long id) {
        return sessionService.getSessionById(id);
    }

    @PostMapping
    public Session createSession(@RequestBody Session session) {
        return sessionService.createSession(session);
    }

    @PutMapping("/{id}")
    public Session updateSession(@PathVariable Long id, @RequestBody Session sessionDetails) {
        return sessionService.updateSession(id, sessionDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
    }

    @PostMapping("/{sessionId}/join/{userId}")
    public void addUserToSession(@PathVariable Long sessionId, @PathVariable Long userId) {
        sessionService.addUserToSession(sessionId, userId);
    }

    @PostMapping("/{sessionId}/end")
    public RestaurantDTO endSession(@PathVariable Long sessionId) {
        sessionService.endSession(sessionId);
        return sessionService.pickRandomRestaurant(sessionId);
    }
}
