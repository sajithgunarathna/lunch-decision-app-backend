package com.gov.lunchsession.controller;

import com.gov.lunchsession.entity.Restaurant;
import com.gov.lunchsession.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void testAddRestaurantToSession() throws Exception {
        Restaurant mockRestaurant = new Restaurant();
        mockRestaurant.setId(1L);
        mockRestaurant.setName("Restaurant 1");

        Mockito.when(restaurantService.addRestaurantToSession(any(Long.class), any(Long.class), any(Restaurant.class)))
                .thenReturn(mockRestaurant);

        mockMvc.perform(post("/api/restaurants/session/1/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Restaurant 1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Restaurant 1"));
    }

    @Test
    public void testGetRestaurantById() throws Exception {
        Restaurant mockRestaurant = new Restaurant();
        mockRestaurant.setId(1L);
        mockRestaurant.setName("Restaurant 1");

        Mockito.when(restaurantService.getRestaurantById(1L)).thenReturn(mockRestaurant);

        mockMvc.perform(get("/api/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Restaurant 1"));
    }

    // Add more tests for other endpoints
}
