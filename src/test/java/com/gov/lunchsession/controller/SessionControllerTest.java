package com.gov.lunchsession.controller;

import com.gov.lunchsession.entity.Session;
import com.gov.lunchsession.service.SessionService;
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

@WebMvcTest(SessionController.class)
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @Test
    public void testCreateSession() throws Exception {
        Session mockSession = new Session();
        mockSession.setId(1L);
        mockSession.setName("Lunch Session");

        Mockito.when(sessionService.createSession(any(Session.class))).thenReturn(mockSession);

        mockMvc.perform(post("/api/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Lunch Session\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Lunch Session"));
    }

    @Test
    public void testGetSessionById() throws Exception {
        Session mockSession = new Session();
        mockSession.setId(1L);
        mockSession.setName("Lunch Session");

        Mockito.when(sessionService.getSessionById(1L)).thenReturn(mockSession);

        mockMvc.perform(get("/api/sessions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Lunch Session"));
    }

    @Test
    public void testAddUserToSession() throws Exception {
        mockMvc.perform(post("/api/sessions/1/join/1"))
                .andExpect(status().isOk());

        Mockito.verify(sessionService, Mockito.times(1)).addUserToSession(1L, 1L);
    }

    // Add more tests for other endpoints
}
