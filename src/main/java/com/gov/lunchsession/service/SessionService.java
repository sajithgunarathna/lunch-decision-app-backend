package com.gov.lunchsession.service;

import com.gov.lunchsession.dto.RestaurantDTO;
import com.gov.lunchsession.entity.Restaurant;
import com.gov.lunchsession.entity.Session;
import com.gov.lunchsession.entity.User;
import com.gov.lunchsession.repository.SessionRepository;
import com.gov.lunchsession.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Session getSessionById(Long id) {
        return sessionRepository.findById(id).orElseThrow(() -> new RuntimeException("Session not found"));
    }

    public Session createSession(Session session) {
        session.setActive(true);
        return sessionRepository.save(session);
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    public Session updateSession(Long id, Session sessionDetails) {
        Session session = sessionRepository.findById(id).orElseThrow(() -> new RuntimeException("Session not found"));

        session.setName(sessionDetails.getName());
        session.setActive(sessionDetails.isActive());

        return sessionRepository.save(session);
    }

    public void addUserToSession(Long sessionId, Long userId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Session not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (!session.isActive()) {
            throw new RuntimeException("Cannot join a session that has already ended");
        }

        session.getParticipants().add(user);
        sessionRepository.save(session);
    }

    public Session endSession(Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Session not found"));
        session.setActive(false);

        return sessionRepository.save(session);
    }

    public RestaurantDTO pickRandomRestaurant(Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Session not found"));
        List<Restaurant> restaurantsList = restaurantService.getAllRestaurants();
        Set<Restaurant> restaurants =restaurantsList.stream().filter(restaurant -> restaurant.getSession().equals(session)).collect(Collectors.toSet());

        if (restaurants.isEmpty()) {
            throw new RuntimeException("No restaurants to pick from");
        }

        int randomIndex = new Random().nextInt(restaurants.size());
        Restaurant restaurant=restaurants.toArray(new Restaurant[0])[randomIndex];
        return new RestaurantDTO(restaurant.getId(),restaurant.getName(),restaurant.getSession().getId());
    }
}
