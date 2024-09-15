package com.Perseo.service;

import com.Perseo.model.Experience;
import com.Perseo.repository.IExperienceRepository;
import com.Perseo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    private final IExperienceRepository experienceRepository;

    @Autowired
    public ExperienceService(IExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public Experience saveExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    public List<Experience> getExperiencesByUserId(Long userId) {
        return experienceRepository.findByUserId(userId);
    }

    public Experience getExperienceById(Long id) {
        return experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with ID: " + id));
    }

    public Experience updateExperience(Long id, Experience updatedExperience) {
        Experience existingExperience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with ID: " + id));

        existingExperience.setTitle(updatedExperience.getTitle());
        existingExperience.setCompany(updatedExperience.getCompany());
        existingExperience.setDescription(updatedExperience.getDescription());
        existingExperience.setStartDate(updatedExperience.getStartDate());
        existingExperience.setEndDate(updatedExperience.getEndDate());
        existingExperience.setUser(updatedExperience.getUser());

        return experienceRepository.save(existingExperience);
    }

    public void deleteExperience(Long id) {
        if (!experienceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Experience not found with ID: " + id);
        }

        experienceRepository.deleteById(id);
    }
}
