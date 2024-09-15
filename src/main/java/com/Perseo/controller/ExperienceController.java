package com.Perseo.controller;

import com.Perseo.exception.ResourceNotFoundException;
import com.Perseo.model.Experience;
import com.Perseo.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;

    @Autowired
    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @PostMapping
    public ResponseEntity<Experience> createExperience(@RequestBody Experience experience) {
        Experience savedExperience = experienceService.saveExperience(experience);
        return new ResponseEntity<>(savedExperience, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Experience> getExperienceById(@PathVariable Long id) {
        try {
            Experience experience = experienceService.getExperienceById(id);
            return new ResponseEntity<>(experience, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Experience>> getExperiencesByUserId(@PathVariable Long userId) {
        List<Experience> experiences = experienceService.getExperiencesByUserId(userId);
        return new ResponseEntity<>(experiences, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Experience> updateExperience(@PathVariable Long id, @RequestBody Experience updatedExperience) {
        try {
            Experience experience = experienceService.updateExperience(id, updatedExperience);
            return new ResponseEntity<>(experience, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        try {
            experienceService.deleteExperience(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
