package com.gov.lunchsession.service;

import com.gov.lunchsession.entity.Restaurant;
import com.gov.lunchsession.entity.Session;
import com.gov.lunchsession.entity.User;
import com.gov.lunchsession.repository.RestaurantRepository;
import com.gov.lunchsession.repository.SessionRepository;
import com.gov.lunchsession.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    public Restaurant addRestaurantToSession(Long sessionId, Long userId, Restaurant restaurant) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Session not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (!session.isActive()) {
            throw new RuntimeException("Cannot add restaurant to an ended session");
        }

        restaurant.setSession(session);
        restaurant.setSubmittedBy(user);

        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
}
