package com.Perseo.service;

import com.Perseo.model.Experience;
import com.Perseo.repository.IExperienceRepository;
import com.Perseo.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExperienceServiceTest {

    @InjectMocks
    private ExperienceService experienceService;

    @Mock
    private IExperienceRepository experienceRepository;

    private Experience experience;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        experience = Experience.builder()
                .id(1L)
                .title("Software Engineer")
                .company("Tech Corp")
                .description("Developed software solutions")
                .startDate("2020-01-01")
                .endDate("2023-01-01")
                .build();
    }

    @Test
    public void testSaveExperience() {
        when(experienceRepository.save(any(Experience.class))).thenReturn(experience);

        Experience savedExperience = experienceService.saveExperience(experience);

        assertNotNull(savedExperience);
        assertEquals("Software Engineer", savedExperience.getTitle());
        verify(experienceRepository, times(1)).save(experience);
    }

    @Test
    public void testGetExperiencesByUserId() {
        List<Experience> experiences = Arrays.asList(experience);
        when(experienceRepository.findByUserId(1L)).thenReturn(experiences);

        List<Experience> result = experienceService.getExperiencesByUserId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Software Engineer", result.get(0).getTitle());
        verify(experienceRepository, times(1)).findByUserId(1L);
    }

    @Test
    public void testGetExperienceById() {
        when(experienceRepository.findById(1L)).thenReturn(Optional.of(experience));

        Experience foundExperience = experienceService.getExperienceById(1L);

        assertNotNull(foundExperience);
        assertEquals("Software Engineer", foundExperience.getTitle());
        verify(experienceRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetExperienceById_NotFound() {
        when(experienceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            experienceService.getExperienceById(1L);
        });
    }

    @Test
    public void testUpdateExperience() {
        Experience updatedExperience = Experience.builder()
                .id(1L)
                .title("Senior Software Engineer")
                .company("Tech Corp")
                .description("Led software development teams")
                .startDate("2020-01-01")
                .endDate("2024-01-01")
                .build();

        when(experienceRepository.findById(1L)).thenReturn(Optional.of(experience));
        when(experienceRepository.save(any(Experience.class))).thenReturn(updatedExperience);

        Experience result = experienceService.updateExperience(1L, updatedExperience);

        assertNotNull(result);
        assertEquals("Senior Software Engineer", result.getTitle());
        verify(experienceRepository, times(1)).findById(1L);
        verify(experienceRepository, times(1)).save(updatedExperience);
    }

    @Test
    public void testUpdateExperience_NotFound() {
        Experience updatedExperience = Experience.builder()
                .id(1L)
                .title("Senior Software Engineer")
                .company("Tech Corp")
                .description("Led software development teams")
                .startDate("2020-01-01")
                .endDate("2024-01-01")
                .build();

        when(experienceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            experienceService.updateExperience(1L, updatedExperience);
        });
    }

    @Test
    public void testDeleteExperience() {
        when(experienceRepository.existsById(1L)).thenReturn(true);

        experienceService.deleteExperience(1L);

        verify(experienceRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteExperience_NotFound() {
        when(experienceRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            experienceService.deleteExperience(1L);
        });
    }
}
