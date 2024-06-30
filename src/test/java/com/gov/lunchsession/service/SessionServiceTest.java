package com.gov.lunchsession.service;

import com.gov.lunchsession.dto.RestaurantDTO;
import com.gov.lunchsession.entity.Restaurant;
import com.gov.lunchsession.entity.Session;
import com.gov.lunchsession.entity.User;
import com.gov.lunchsession.repository.SessionRepository;
import com.gov.lunchsession.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    public SessionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSession() {
        Session mockSession = new Session();
        mockSession.setId(1L);
        mockSession.setName("Lunch Session");

        when(sessionRepository.save(any(Session.class))).thenReturn(mockSession);

        Session createdSession = sessionService.createSession(mockSession);

        assertNotNull(createdSession);
        assertEquals(1L, createdSession.getId());
    }

    @Test
    public void testAddUserToSession_SessionExists() {
        Session mockSession = new Session();
        mockSession.setId(1L);
        mockSession.setActive(true);

        User mockUser = new User();
        mockUser.setId(1L);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        sessionService.addUserToSession(1L, 1L);

        verify(sessionRepository, times(1)).save(mockSession);
        assertTrue(mockSession.getParticipants().contains(mockUser));
    }

    @Test
    public void testEndSession() {
        Session mockSession = new Session();
        mockSession.setId(1L);
        mockSession.setActive(true);
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));

        sessionService.endSession(1L);

        verify(sessionRepository, times(1)).save(mockSession);
        assertFalse(mockSession.isActive());
    }

    @Test
    public void testPickRandomRestaurant() {
        Session mockSession = new Session();
        mockSession.setId(1L);
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName("Restaurant 1");
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("Restaurant 2");

        mockSession.setRestaurants(Set.of(restaurant1, restaurant2));
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));

        RestaurantDTO pickedRestaurant = sessionService.pickRandomRestaurant(1L);

        assertNotNull(pickedRestaurant);
        assertTrue(mockSession.getRestaurants().contains(pickedRestaurant));
    }

    // Add more tests for other methods
}
