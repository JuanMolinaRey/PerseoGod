package com.Perseo.service;

import com.Perseo.model.Experience;
import com.Perseo.repository.IExperienceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExperienceServiceTest {

    @InjectMocks
    private ExperienceService experienceService;

    @Mock
    private IExperienceRepository experienceRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveExperience() {
        Experience experience = Experience.builder()
                .id(1L)
                .title("Developer")
                .company("TechCorp")
                .description("Developed applications")
                .startDate("2023-01-01")
                .build();

        when(experienceRepository.save(any(Experience.class))).thenReturn(experience);

        Experience savedExperience = experienceService.saveExperience(experience);

        assertNotNull(savedExperience);
        assertEquals("Developer", savedExperience.getTitle());
        verify(experienceRepository, times(1)).save(experience);
    }

    @Test
    public void testGetExperiencesByUserId() {
        Experience experience = Experience.builder()
                .id(1L)
                .title("Developer")
                .company("TechCorp")
                .description("Developed applications")
                .startDate("2023-01-01")
                .build();

        when(experienceRepository.findByUserId(1L)).thenReturn(List.of(experience));

        List<Experience> experiences = experienceService.getExperiencesByUserId(1L);

        assertFalse(experiences.isEmpty());
        assertEquals(1, experiences.size());
        assertEquals("Developer", experiences.get(0).getTitle());
        verify(experienceRepository, times(1)).findByUserId(1L);
    }

    @Test
    public void testGetExperienceById() {
        Experience experience = Experience.builder()
                .id(1L)
                .title("Developer")
                .company("TechCorp")
                .description("Developed applications")
                .startDate("2023-01-01")
                .build();

        when(experienceRepository.findById(1L)).thenReturn(Optional.of(experience));

        Experience foundExperience = experienceService.getExperienceById(1L);

        assertNotNull(foundExperience);
        assertEquals("Developer", foundExperience.getTitle());
        verify(experienceRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetExperienceById_NotFound() {
        when(experienceRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            experienceService.getExperienceById(1L);
        });

        assertEquals("Experience not found", exception.getMessage());
        verify(experienceRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteExperience() {
        doNothing().when(experienceRepository).deleteById(1L);

        assertDoesNotThrow(() -> experienceService.deleteExperience(1L));
        verify(experienceRepository, times(1)).deleteById(1L);
    }
}
