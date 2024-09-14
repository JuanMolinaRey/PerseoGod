package com.Perseo.service;

import com.Perseo.model.Experience;
import com.Perseo.repository.IExperienceRepository;
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
        return experienceRepository.findById(id).orElseThrow(() -> new RuntimeException("Experience not found"));
    }

    public void deleteExperience(Long id) {
        experienceRepository.deleteById(id);
    }
}

