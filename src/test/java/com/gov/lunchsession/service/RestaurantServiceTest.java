package com.gov.lunchsession.service;

import com.gov.lunchsession.entity.Restaurant;
import com.gov.lunchsession.entity.Session;
import com.gov.lunchsession.entity.User;
import com.gov.lunchsession.repository.RestaurantRepository;
import com.gov.lunchsession.repository.SessionRepository;
import com.gov.lunchsession.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    public RestaurantServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRestaurantToSession_SessionActive() {
        Session mockSession = new Session();
        mockSession.setId(1L);
        mockSession.setActive(true);

        User mockUser = new User();
        mockUser.setId(1L);

        Restaurant mockRestaurant = new Restaurant();
        mockRestaurant.setName("Restaurant 1");

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(mockRestaurant);

        Restaurant addedRestaurant = restaurantService.addRestaurantToSession(1L, 1L, mockRestaurant);

        assertNotNull(addedRestaurant);
        assertEquals("Restaurant 1", addedRestaurant.getName());
        verify(restaurantRepository, times(1)).save(mockRestaurant);
    }

    @Test
    public void testAddRestaurantToSession_SessionEnded() {
        Session mockSession = new Session();
        mockSession.setId(1L);
        mockSession.setActive(false);

        User mockUser = new User();
        mockUser.setId(1L);

        Restaurant mockRestaurant = new Restaurant();
        mockRestaurant.setName("Restaurant 1");

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            restaurantService.addRestaurantToSession(1L, 1L, mockRestaurant);
        });

        assertEquals("Cannot add restaurant to an ended session", exception.getMessage());
    }

    // Add more tests for other methods
}
