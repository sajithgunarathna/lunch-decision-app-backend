package com.gov.lunchsession.controller;

import com.gov.lunchsession.dto.RestaurantDTO;
import com.gov.lunchsession.entity.Restaurant;
import com.gov.lunchsession.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return restaurants.stream()
                .map(restaurant -> new RestaurantDTO(restaurant.getId(), restaurant.getName(),restaurant.getSession().getId()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{sessionId}/restaurants")
    public List<RestaurantDTO> getAllRestaurantsBySessionId(@PathVariable Long sessionId) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return restaurants.stream()
                .filter(restaurant -> !restaurant.getSession().equals(sessionId))
                .map(restaurant -> new RestaurantDTO(restaurant.getId(), restaurant.getName(),restaurant.getSession().getId()))
                .collect(Collectors.toList());
    }

    @PostMapping("/session/{sessionId}/user/{userId}")
    public RestaurantDTO addRestaurantToSession(@PathVariable Long sessionId, @PathVariable Long userId, @RequestBody Restaurant restaurant) {
        Restaurant restaurant1=restaurantService.addRestaurantToSession(sessionId, userId, restaurant);
        RestaurantDTO restaurantDTO=new RestaurantDTO(restaurant1.getId(),restaurant1.getName(),restaurant1.getSession().getId());

        return restaurantDTO;
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }
}
