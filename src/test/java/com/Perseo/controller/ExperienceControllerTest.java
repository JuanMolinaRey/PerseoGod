package com.Perseo.controller;

import com.Perseo.exception.ResourceNotFoundException;
import com.Perseo.model.Experience;
import com.Perseo.service.ExperienceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ExperienceControllerTest {

    @Mock
    private ExperienceService experienceService;

    @InjectMocks
    private ExperienceController experienceController;

    private Experience experience;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        experience = new Experience();
        experience.setId(1L);
        experience.setTitle("Developer");
        experience.setCompany("Tech Inc.");
        experience.setDescription("Developed applications.");
        experience.setStartDate("2022-01-01");
        experience.setEndDate("2023-01-01");
    }

    @Test
    public void test_Create_Experience() {
        when(experienceService.saveExperience(any(Experience.class))).thenReturn(experience);

        ResponseEntity<Experience> response = experienceController.createExperience(experience);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(experience, response.getBody());
    }

    @Test
    public void test_Get_Experience_By_Id() {
        when(experienceService.getExperienceById(anyLong())).thenReturn(experience);

        ResponseEntity<Experience> response = experienceController.getExperienceById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(experience, response.getBody());
    }

    @Test
    public void test_Get_Experiences_By_User_Id() {
        List<Experience> experiences = List.of(experience);
        when(experienceService.getExperiencesByUserId(anyLong())).thenReturn(experiences);

        ResponseEntity<List<Experience>> response = experienceController.getExperiencesByUserId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(experiences, response.getBody());
    }

    @Test
    public void test_Update_Experience() {
        when(experienceService.updateExperience(anyLong(), any(Experience.class))).thenReturn(experience);

        ResponseEntity<Experience> response = experienceController.updateExperience(1L, experience);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(experience, response.getBody());
    }

    @Test
    public void test_Delete_Experience() {
        doNothing().when(experienceService).deleteExperience(anyLong());

        ResponseEntity<Void> response = experienceController.deleteExperience(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
