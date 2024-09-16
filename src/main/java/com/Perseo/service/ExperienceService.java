package com.Perseo.service;

import com.Perseo.model.Experience;
import com.Perseo.repository.IExperienceRepository;
import com.Perseo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    private final IExperienceRepository iExperienceRepository;

    @Autowired
    public ExperienceService(IExperienceRepository experienceRepository) {
        this.iExperienceRepository = experienceRepository;
    }

    public Experience saveExperience(Experience experience) {
        return iExperienceRepository.save(experience);
    }

    public List<Experience> getExperiencesByUserId(Long userId) {
        return iExperienceRepository.findByUserId(userId);
    }

    public Experience getExperienceById(Long id) {
        return iExperienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with ID: " + id));
    }

    public Experience updateExperience(Long id, Experience updatedExperience) {
        Experience existingExperience = iExperienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with ID: " + id));

        existingExperience.setTitle(updatedExperience.getTitle());
        existingExperience.setCompany(updatedExperience.getCompany());
        existingExperience.setDescription(updatedExperience.getDescription());
        existingExperience.setStartDate(updatedExperience.getStartDate());
        existingExperience.setEndDate(updatedExperience.getEndDate());
        existingExperience.setUser(updatedExperience.getUser());

        return iExperienceRepository.save(existingExperience);
    }

    public void deleteExperience(Long id) {
        if (!iExperienceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Experience not found with ID: " + id);
        }

        iExperienceRepository.deleteById(id);
    }
}
