package com.Perseo.controller;

import com.Perseo.model.Experience;
import com.Perseo.service.ExperienceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExperienceControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ExperienceController experienceController;

    @Mock
    private ExperienceService experienceService;

    private Experience experience;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(experienceController).build();

        experience = Experience.builder()
                .id(1L)
                .title("Developer")
                .company("TechCorp")
                .description("Developed applications")
                .startDate("2023-01-01")
                .build();
    }

    @Test
    public void testCreateExperience() throws Exception {
        when(experienceService.saveExperience(any(Experience.class))).thenReturn(experience);

        mockMvc.perform(post("/experiences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Developer\",\"company\":\"TechCorp\",\"description\":\"Developed applications\",\"startDate\":\"2023-01-01\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Developer"));
    }

    @Test
    public void testGetExperienceById() throws Exception {
        when(experienceService.getExperienceById(1L)).thenReturn(experience);

        mockMvc.perform(get("/experiences/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Developer"));
    }

    @Test
    public void testGetExperiencesByUserId() throws Exception {
        when(experienceService.getExperiencesByUserId(1L)).thenReturn(List.of(experience));

        mockMvc.perform(get("/experiences/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Developer"));
    }

    @Test
    public void testDeleteExperience() throws Exception {
        doNothing().when(experienceService).deleteExperience(1L);

        mockMvc.perform(delete("/experiences/1"))
                .andExpect(status().isNoContent());
    }
}
